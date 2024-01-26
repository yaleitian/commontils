package com.example.demoboot.utils.deletefile;

import com.example.demoboot.utils.readfile.FormatSize;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DirectoryCleanerMaven {

    private static final Logger LOGGER = Logger.getLogger(DirectoryCleanerMaven.class.getName());
    private static final List<FileDetails> filesToDelete = new ArrayList<>();

    static {
        try {
            FileHandler fileHandler = new FileHandler("directory_cleaner.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to set up logger FileHandler", e);
        }
    }

    public static void main(String[] args) {
//        Path startPath = Paths.get("/Users/tianyalei/Desktop/audit/rep");
        Path startPath = Paths.get("/Users/tianyalei/Documents/document/maven/repository");
        try {
            // 第一步：收集文件
            Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (shouldDelete(file)) {
                        filesToDelete.add(new FileDetails(file, attrs.size()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    // 对于目录，只有当它们为空且满足shouldDelete条件时，才将其添加到待删除列表
                    if (Files.isDirectory(dir) && directoryIsEmpty(dir) && shouldDelete(dir)) {
                        filesToDelete.add(new FileDetails(dir, 0));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });


            // 第二步：排序并删除文件
            filesToDelete.sort(Comparator.comparingLong(FileDetails::getSize).reversed());
            for (FileDetails file : filesToDelete) {
                try {
                    Files.delete(file.getPath());
                    LOGGER.info("Deleted file: " + file.getPath() + ", Size: " + FormatSize.formatSize(file.getSize()));
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Failed to delete file: " + file.getPath(), e);
                }
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to walk file tree at: " + startPath, e);
        }
    }

    private static boolean shouldDelete(Path path) {
        // 如果路径是 .svn 目录的一部分，则不删除
        if (path.toString().contains(File.separator + ".svn" + File.separator)) {
            return false;
        }

        // 如果是文件，检查是否以 .jar 或 .pom 结尾
        if (Files.isRegularFile(path)) {
            String fileName = path.getFileName().toString();
            return !fileName.endsWith(".jar") && !fileName.endsWith(".pom");
        }

        // 如果是目录，允许删除（如果它是空的）
        if (Files.isDirectory(path)) {
            try {
                return directoryIsEmpty(path);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error checking if directory is empty: " + path, e);
                return false;
            }
        }

        return false;
    }

    private static boolean directoryIsEmpty(Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }
}

class FileDetails {
    private final Path path;
    private final long size;

    public FileDetails(Path path, long size) {
        this.path = path;
        this.size = size;
    }

    public Path getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }
}

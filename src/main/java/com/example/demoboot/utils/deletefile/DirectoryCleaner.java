package com.example.demoboot.utils.deletefile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryCleaner {
    private static final Logger LOGGER = Logger.getLogger(DirectoryCleanerMaven.class.getName());

    public static void main(String[] args) {
        // 指定的起始目录
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        cleanDirectory(directory);
    }

    private static void cleanDirectory(File directory) {
        if (!directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            // 删除空目录
            deleteDirectory(directory);
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // 递归处理子目录
                cleanDirectory(file);
            } else {
                // 检查文件是否需要保留
                if (!shouldKeep(file)) {
                    deleteFile(file);
                }
            }
        }

        // 递归处理后再次检查目录是否为空
        files = directory.listFiles();
        if (files == null || files.length == 0) {
            deleteDirectory(directory);
        }
    }

    private static boolean shouldKeep(File file) {
        try {
            if (!file.getName().endsWith(".java")) {
                return false;
            }
            BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            LocalDate lastModified = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate cutoffDate = LocalDate.of(2023, 10, 16); // 指定日期
            return lastModified.isAfter(cutoffDate) || lastModified.isEqual(cutoffDate);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to set up logger FileHandler", e);
            return false;
        }
    }

    private static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Deleted file: " + file.getAbsolutePath());
        } else {
            System.out.println("Failed to delete file: " + file.getAbsolutePath());
        }
    }

    private static void deleteDirectory(File directory) {
        if (directory.delete()) {
            System.out.println("Deleted empty directory: " + directory.getAbsolutePath());
        } else {
            System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
        }
    }
}

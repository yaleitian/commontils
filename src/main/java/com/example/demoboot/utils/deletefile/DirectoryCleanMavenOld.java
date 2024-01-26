package com.example.demoboot.utils.deletefile;

import java.io.File;

public class DirectoryCleanMavenOld {

    public static void main(String[] args) {
        // 指定的起始目录
        File directory = new File("/Users/tianyalei/Desktop/audit/rep");
        cleanDirectory(directory);
    }

    private static void cleanDirectory(File directory) {
        if (!directory.isDirectory()) {
            return;
        }

        // 如果是 .svn 目录，直接返回，不做任何处理
        if (directory.getName().equals(".svn")) {
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
        // 只保留以 .jar 和 .pom 结尾的文件
        return file.getName().endsWith(".jar") || file.getName().endsWith(".pom");
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

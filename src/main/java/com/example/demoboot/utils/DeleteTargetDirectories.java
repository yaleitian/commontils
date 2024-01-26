package com.example.demoboot.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteTargetDirectories {

    public static void main(String[] args) {
        // 指定的起始目录
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        deleteTargetDirectories(directory);
    }

    private static void deleteTargetDirectories(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if ("schedule".equals(file.getName())) {
                        // 删除名为 "target" 的目录及其内容
                        try {
                            deleteDirectoryRecursively(file);
                            System.out.println("Deleted directory: " + file.getAbsolutePath());
                        } catch (IOException e) {
                            System.err.println("Failed to delete: " + file.getAbsolutePath());
                            e.printStackTrace();
                        }
                    } else {
                        // 递归搜索其他目录
                        deleteTargetDirectories(file);
                    }
                }
            }
        }
    }

    private static void deleteDirectoryRecursively(File directory) throws IOException {
        if (directory.isDirectory()) {
            File[] entries = directory.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectoryRecursively(entry);
                }
            }
        }
        if (!directory.delete()) {
            throw new IOException("Failed to delete " + directory);
        }
    }
}

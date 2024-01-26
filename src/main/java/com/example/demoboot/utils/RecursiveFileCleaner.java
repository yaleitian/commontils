package com.example.demoboot.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;

public class RecursiveFileCleaner {

    public static void main(String[] args) {
        // 指定目录
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        deleteOldFiles(directory);
    }

    private static void deleteOldFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是目录，则递归处理
                    deleteOldFiles(file);
                } else if (shouldDelete(file)) {
                    // 如果是文件，检查是否应该删除
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Deleted: " + file.getAbsolutePath());
                    } else {
                        System.out.println("Failed to delete: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static boolean shouldDelete(File file) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            LocalDate lastModified = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate cutoffDate = LocalDate.of(2023, 12, 6); // 指定日期
            return lastModified.isBefore(cutoffDate); // 删除所有在指定日期之前的文件
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

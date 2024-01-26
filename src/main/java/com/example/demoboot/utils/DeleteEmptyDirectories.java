package com.example.demoboot.utils;

import java.io.File;

public class DeleteEmptyDirectories {

    public static void main(String[] args) {
        // 指定的起始目录
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        deleteEmptyDirectories(directory);
    }

    private static void deleteEmptyDirectories(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                // 如果是目录，则递归调用此方法
                if (file.isDirectory()) {
                    deleteEmptyDirectories(file);
                }
            }
        }

        // 重新检查目录是否为空，因为可能在递归过程中被清空
        files = directory.listFiles();
        if (files == null || files.length == 0) {
            if (directory.delete()) {
                System.out.println("Deleted empty directory: " + directory.getAbsolutePath());
            } else {
                System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
            }
        }
    }
}

package com.example.demoboot.utils;

import java.io.File;

public class FileDeleter {

    public static void main(String[] args) {
        // 指定目录
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        deleteNonJavaFiles(directory);
    }

    private static void deleteNonJavaFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是目录，则递归处理
                    deleteNonJavaFiles(file);
                } else if (!file.getName().endsWith(".java")) {
                    // 如果文件后缀不是.java，则删除文件
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
}

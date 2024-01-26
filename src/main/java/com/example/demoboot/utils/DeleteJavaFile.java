package com.example.demoboot.utils;

import java.io.File;

public class DeleteJavaFile {
    private static final String TARGET_DIRECTORY = "/Users/tianyalei/Desktop/audit/risk-single";
    private static final String FILE_TO_DELETE = "StringVsStringBuilder.java"; // 替换为要删除的文件名

    public static void main(String[] args) {
        File directory = new File(TARGET_DIRECTORY);
        deleteFileRecursively(directory, FILE_TO_DELETE);
    }

    private static void deleteFileRecursively(File directory, String fileName) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是目录，递归处理
                    deleteFileRecursively(file, fileName);
                } else if (file.getName().equals(fileName)) {
                    // 找到匹配的文件并尝试删除
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Deleted file: " + file.getAbsolutePath());
                    } else {
                        System.out.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

}

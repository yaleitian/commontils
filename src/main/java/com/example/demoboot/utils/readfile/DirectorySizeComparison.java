package com.example.demoboot.utils.readfile;

import java.io.File;

import static com.example.demoboot.utils.readfile.FormatSize.formatSize;

public class DirectorySizeComparison {

    public static void main(String[] args) {
        // 指定两个目录
        String path1 = "/Users/tianyalei/Documents/document/maven/repository";
        String path2 = "/Users/tianyalei/Desktop/audit/rep";

        // 获取删除前的大小
        long sizeBefore1 = calculateDirectorySize(new File(path1));
        long sizeBefore2 = calculateDirectorySize(new File(path2));
        System.out.println("Before deletion:");
        System.out.println("Size of " + path1 + ": " + formatSize(sizeBefore1));
        System.out.println("Size of " + path2 + ": " + formatSize(sizeBefore2));

        // 执行删除操作

        // 获取删除后的大小
        long sizeAfter1 = calculateDirectorySize(new File(path1));
        long sizeAfter2 = calculateDirectorySize(new File(path2));
        System.out.println("After deletion:");
        System.out.println("Size of " + path1 + ": " + formatSize(sizeAfter1));
        System.out.println("Size of " + path2 + ": " + formatSize(sizeAfter2));
    }

    private static long calculateDirectorySize(File directory) {
        if (directory.isFile()) {
            return directory.length();
        }
        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                size += calculateDirectorySize(file);
            }
        }
        return size;
    }
}


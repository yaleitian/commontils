package com.example.demoboot.utils.readfile;

import java.io.File;
import java.util.*;

import static com.example.demoboot.utils.readfile.FormatSize.formatSize;

public class TopDirectories {

    public static void main(String[] args) {
        File startDir = new File("/Users/tianyalei/Documents/document/maven/repository");

        // 获取目录大小映射
        Map<File, Long> directorySizes = new HashMap<>();
        calculateDirectorySize(startDir, directorySizes);

        // 创建一个列表并按大小排序
        List<Map.Entry<File, Long>> sortedEntries = new ArrayList<>(directorySizes.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // 打印前十大目录
        int topCount = 10;
        for (int i = 0; (i < topCount) && (i < sortedEntries.size()); i++) {
            Map.Entry<File, Long> entry = sortedEntries.get(i);
            System.out.println("Directory: " + entry.getKey() + ", Size: " + formatSize(entry.getValue()));
        }
    }

    private static long calculateDirectorySize(File directory, Map<File, Long> directorySizes) {
        long size = 0;

        // 确保是一个目录
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        size += calculateDirectorySize(file, directorySizes);
                    } else {
                        size += file.length();
                    }
                }
            }
            directorySizes.put(directory, size);
        }

        return size;
    }
}


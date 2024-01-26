package com.example.demoboot.utils.readfile;

import java.io.File;
import java.util.*;

import static com.example.demoboot.utils.readfile.FormatSize.formatSize;

public class Top100Files {

    public static void main(String[] args) {
        File startDir = new File("/Users/tianyalei/Documents/document/maven/repository");

        // 获取文件大小映射
        Map<File, Long> fileSizes = new HashMap<>();
        calculateFileSize(startDir, fileSizes);

        // 创建一个列表并按大小排序
        List<Map.Entry<File, Long>> sortedEntries = new ArrayList<>(fileSizes.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // 打印前100个文件
        int topCount = 100;
        for (int i = 0; (i < topCount) && (i < sortedEntries.size()); i++) {
            Map.Entry<File, Long> entry = sortedEntries.get(i);
            System.out.println("File: " + entry.getKey() + ", Size: " + formatSize(entry.getValue()));
        }
    }

    private static void calculateFileSize(File directory, Map<File, Long> fileSizes) {
        // 确保是一个目录
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        calculateFileSize(file, fileSizes);
                    } else {
                        fileSizes.put(file, file.length());
                    }
                }
            }
        }
    }

}

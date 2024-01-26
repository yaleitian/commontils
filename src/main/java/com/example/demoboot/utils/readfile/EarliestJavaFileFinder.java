package com.example.demoboot.utils.readfile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EarliestJavaFileFinder {
    private File earliestFile = null;
    private long earliestDate = Long.MAX_VALUE;

    public void findEarliestJavaFile(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        findEarliestJavaFile(file); // 递归调用
                    } else if (file.getName().endsWith(".java")) {
                        long lastModified = file.lastModified();
                        if (lastModified < earliestDate) {
                            earliestDate = lastModified;
                            earliestFile = file;
                        }
                    }
                }
            }
        }
    }

    public void printEarliestFile() {
        if (earliestFile != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(new Date(earliestDate));
            System.out.println("最早的Java文件: " + earliestFile.getAbsolutePath());
            System.out.println("修改日期: " + formattedDate);
        } else {
            System.out.println("没有找到Java文件。");
        }
    }

    public static void main(String[] args) {
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        EarliestJavaFileFinder finder = new EarliestJavaFileFinder();
        finder.findEarliestJavaFile(directory);
        finder.printEarliestFile();
    }
}

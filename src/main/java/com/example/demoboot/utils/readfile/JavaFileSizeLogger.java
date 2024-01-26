package com.example.demoboot.utils.readfile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JavaFileSizeLogger {
    private static final Logger LOGGER = Logger.getLogger(JavaFileSizeLogger.class.getName());
    private final List<File> javaFiles = new ArrayList<>(); // 将 javaFiles 声明为 final

    public JavaFileSizeLogger() {
        try {
            // 设置日志文件路径
            FileHandler fileHandler = new FileHandler("/Users/tianyalei/Desktop/audit/logfilesize.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.warning("FileHandler not working: " + e.getMessage());
        }
    }

    public void collectJavaFiles(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        collectJavaFiles(file); // 递归调用
                    } else if (file.getName().endsWith(".java")) {
                        javaFiles.add(file);
                    }
                }
            }
        }
    }

    public void logJavaFiles() {
        // 按文件大小降序排序
        javaFiles.sort(Comparator.comparingLong(File::length).reversed());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (File file : javaFiles) {
            String formattedDate = dateFormat.format(new Date(file.lastModified()));
            LOGGER.info("文件: " + file.getAbsolutePath() + " - 大小: " + file.length() + " bytes - 修改日期: " + formattedDate);
        }
    }

    public static void main(String[] args) {
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        JavaFileSizeLogger logger = new JavaFileSizeLogger();
        logger.collectJavaFiles(directory);
        logger.logJavaFiles();
    }
}
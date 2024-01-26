package com.example.demoboot.utils.readfile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JavaFileLogger {
    private static final Logger LOGGER = Logger.getLogger(JavaFileLogger.class.getName());
    public JavaFileLogger() {
        try {
            // 设置日志文件路径
            FileHandler fileHandler = new FileHandler("/Users/tianyalei/Desktop/audit/logfile.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.warning("FileHandler not working: " + e.getMessage());
        }
    }
    public void logJavaFiles(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        logJavaFiles(file); // 递归调用
                    } else if (file.getName().endsWith(".java")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = dateFormat.format(new Date(file.lastModified()));
                        LOGGER.info("文件: " + file.getAbsolutePath() + " - 修改日期: " + formattedDate);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        File directory = new File("/Users/tianyalei/Desktop/audit/risk-single");
        JavaFileLogger logger = new JavaFileLogger();
        logger.logJavaFiles(directory);
    }
}

package com.example.demoboot.utils;

public class StringVsStringBuilder {
    public static void main(String[] args) {
        int iterations = 500000;
        String testString = "Test";

        // 测试 String 连接性能
        long startTime = System.currentTimeMillis();
        String resultString = "";
        for (int i = 0; i < iterations; i++) {
            resultString += testString;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("String concatenation time: " + (endTime - startTime) + " ms");

        // 测试 StringBuilder 连接性能
        startTime = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            builder.append(testString);
        }
        String resultBuilder = builder.toString();
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder concatenation time: " + (endTime - startTime) + " ms");
    }
}


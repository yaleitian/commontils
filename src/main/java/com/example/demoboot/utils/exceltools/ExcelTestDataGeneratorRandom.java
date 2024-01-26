package com.example.demoboot.utils.exceltools;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Random;

public class ExcelTestDataGeneratorRandom {
    private static final String EXCEL_FILE_PATH = "/Users/tianyalei/Downloads/生成的测试数据random.xlsx";
    private static final String[] BUSINESS_LINES = {"资产业务", "负债与中间业务", "内控", "财务费用", "信息科技", "线上业务", "其他"};
    private static final String[] RISK_LEVELS = {"严重", "较重", "一般"};
    private static final Random random = new Random();

    public static void main(String[] args) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("TestData");

            int rowIndex = 0;
            int totalEntries = 100; // 总词条数量

            for (int i = 0; i < totalEntries; i++) {
                String primaryEntry = "一级词条_" + random.nextInt(10) + 1;
                String secondaryEntry = "二级词条_" + random.nextInt(10) + 1;
                String tertiaryEntry = "三级词条_" + random.nextInt(10) + 1;
                String quaternaryEntry = "四级词条_" + random.nextInt(10) + 1;

                String businessLine = BUSINESS_LINES[random.nextInt(BUSINESS_LINES.length)];
                String riskLevel = RISK_LEVELS[random.nextInt(RISK_LEVELS.length)];
                String entryDescription = generateRandomChineseWordsFromBusinessLines(10);

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(primaryEntry);
                row.createCell(1).setCellValue(secondaryEntry);
                row.createCell(2).setCellValue(tertiaryEntry);
                row.createCell(3).setCellValue(quaternaryEntry);
                row.createCell(4).setCellValue(businessLine);
                row.createCell(5).setCellValue(riskLevel);
                row.createCell(6).setCellValue(entryDescription);
            }

            // 将workbook写入文件
            FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("测试数据已生成并写入 " + EXCEL_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomChineseWordsFromBusinessLines(int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        while (result.length() < length) {
            String word = BUSINESS_LINES[random.nextInt(BUSINESS_LINES.length)];
            result.append(word);
        }

        return result.substring(0, length);
    }
}


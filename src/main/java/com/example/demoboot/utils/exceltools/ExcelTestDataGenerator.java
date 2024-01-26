package com.example.demoboot.utils.exceltools;

import com.example.demoboot.utils.deletefile.DirectoryCleanerMaven;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ExcelTestDataGenerator {
    private static final Logger LOGGER = Logger.getLogger(DirectoryCleanerMaven.class.getName());

    private static final String EXCEL_FILE_PATH = "/Users/tianyalei/Downloads/生成的测试数据10000.xlsx";
    private static final String[] BUSINESS_LINES = {"资产业务", "负债与中间业务", "内控", "财务费用", "信息科技", "线上业务", "其他"};
    private static final String[] RISK_LEVELS = {"严重", "较重", "一般"};
    private static final Random random = new Random();

    public static void main(String[] args) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("TestData");

            int rowIndex = 0;
            int quaternaryEntryCounter = 1; // 四级词条计数器
            int numberOfPrimaryEntries = 10;
            int numberOfSecondaryEntries = 10;
            int numberOfTertiaryEntries = 10;
            int numberOfQuaternaryEntries = 10;

            for (int i = 1; i <= numberOfPrimaryEntries; i++) {
                String primaryEntry = "一级测试词条_" + i;

                for (int j = 1; j <= numberOfSecondaryEntries; j++) {
                    String secondaryEntry = "二级测试词条_" + j;

                    for (int k = 1; k <= numberOfTertiaryEntries; k++) {
//                        String entryDescription20 = generateRandomChineseWordsFromBusinessLines(20);
                        String tertiaryEntry = "三级测试词条_" + k;
//                        String tertiaryEntry = "三级 词条_" + entryDescription20 + k;

                        for (int l = 1; l <= numberOfQuaternaryEntries; l++) {
                            String quaternaryEntry = "四级测试词条_" + quaternaryEntryCounter++; // 确保唯一性
                            String businessLine = BUSINESS_LINES[random.nextInt(BUSINESS_LINES.length)];
                            String riskLevel = RISK_LEVELS[random.nextInt(RISK_LEVELS.length)];
                            String entryDescription = generateRandomChineseWordsFromBusinessLines(10);

                            Row row = sheet.createRow(rowIndex++);
                            row.createCell(0).setCellValue(primaryEntry);
                            row.createCell(1).setCellValue(secondaryEntry);
                            row.createCell(2).setCellValue(tertiaryEntry);
                            row.createCell(3).setCellValue(quaternaryEntry);
//                            row.createCell(1).setCellValue(secondaryEntry + " (属于: " + primaryEntry + ")");
//                            row.createCell(2).setCellValue(tertiaryEntry + " (属于: " + secondaryEntry + ")");
//                            row.createCell(3).setCellValue(quaternaryEntry + " (属于: " + tertiaryEntry + ")");
                            row.createCell(4).setCellValue(businessLine);
                            row.createCell(5).setCellValue(riskLevel);
                            row.createCell(6).setCellValue(entryDescription);
                        }
                    }
                }
            }

            // 将workbook写入文件
            FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("测试数据已生成并写入 " + EXCEL_FILE_PATH);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to set up logger FileHandler", e);
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

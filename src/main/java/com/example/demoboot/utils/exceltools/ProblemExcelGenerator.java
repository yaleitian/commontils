package com.example.demoboot.utils.exceltools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ProblemExcelGenerator {

    public static void main(String[] args) {
        String[] riskLevels = {"严重", "较重", "一般"};
        String[] problemTypes = {"管理", "制度", "执行"};
        String[] rectificationStatuses = {"未整改", "整改中", "已整改"};
        String[] yesNoOptions = {"是", "否"};

        Random random = new Random();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("问题数据");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {
                "序号", "项目名称", "问题名称", "问题描述", "问题词条",
                "问题风险程度", "问题类型", "主要责任部门",
                "主要责任人", "整改状态", "是否纳入内控评价", "是否问责"
        };
        for (int i = 0; i < columnHeaders.length; i++) {
            headerRow.createCell(i).setCellValue(columnHeaders[i]);
        }

        // Create data rows
        for (int i = 1; i <= 1000; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(i); // 序号
            row.createCell(1).setCellValue("（2023-329）审计项目测试A30");
            row.createCell(2).setCellValue("我是问题导入模板测试数据" + i);
            row.createCell(3).setCellValue("我是问题导入模板问题描述测试数据" + i);
            row.createCell(4).setCellValue("一级词条_2/二级词条_1/三级词条_资产业务财务费用负债与中间业务信息科技信2/四级词条_11");
            row.createCell(5).setCellValue(riskLevels[random.nextInt(riskLevels.length)]);
            row.createCell(6).setCellValue(problemTypes[random.nextInt(problemTypes.length)]);
            row.createCell(7).setCellValue("000103-总行/公司银行部");
            row.createCell(8).setCellValue("6740336937111553-任培");
            row.createCell(9).setCellValue(rectificationStatuses[random.nextInt(rectificationStatuses.length)]);
            row.createCell(10).setCellValue(yesNoOptions[random.nextInt(yesNoOptions.length)]);
            row.createCell(11).setCellValue(yesNoOptions[random.nextInt(yesNoOptions.length)]);
        }

        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream("/Users/tianyalei/Downloads/生成的问题导入测试数据1000.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

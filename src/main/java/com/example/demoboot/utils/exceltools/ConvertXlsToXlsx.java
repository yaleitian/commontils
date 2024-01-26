package com.example.demoboot.utils.exceltools;

import com.spire.xls.ExcelVersion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


public class ConvertXlsToXlsx {
    public static void main(String[] args) {
        try {
            // 创建Workbook对象，用于操作Excel文件
             com.spire.xls.Workbook workbook = new com.spire.xls.Workbook();

            // 加载.xls文件
            workbook.loadFromFile("/Users/tianyalei/Downloads/2348567新建 XLSX 工作表.xls");

            // 保存为.xlsx格式
            workbook.saveToFile("/Users/tianyalei/Downloads/2348567新建 XLSX 工作表.xlsx", ExcelVersion.Version2013);

            /*// Source file path (.xls)
            String filePath = "/Users/tianyalei/Downloads/测试xls.xls";
            // Destination file path (.xlsx)
            String filePathxlsx = "/Users/tianyalei/Downloads/测试xls.xlsx";

            // Read the .xls file to a byte array
            byte[] buf = readFileToByteArray(filePath);

            // Convert the .xls file (in byte array form) to .xlsx format
            byte[] convertedBuf = convertXlsToXlsx(buf);

            // Write the converted byte array to the .xlsx file
            writeFileFromByteArray(convertedBuf, filePathxlsx);

            System.out.println("Converted file size in bytes: " + convertedBuf.length);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static byte[] readFileToByteArray(String filePath) throws Exception {
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            int nRead;
            byte[] data = new byte[1024];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            return buffer.toByteArray();
        }
    }

    private static void writeFileFromByteArray(byte[] buf, String filePath) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(buf);
            fileOutputStream.flush();
        }
    }
    public static byte[] convertXlsToXlsx(byte[] buf) {
        try {
            if (FileMagic.valueOf(buf) == FileMagic.OLE2) {
                // 使用HSSFWorkbook打开旧版Excel文件（.xls）
                try (InputStream inputStream = new ByteArrayInputStream(buf);
                     HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

                    // 创建一个新的XSSFWorkbook
                    XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

                    // 复制每个Sheet的内容
                    for (int i = 0; i < hssfWorkbook.getNumberOfSheets(); i++) {
                        Sheet oldSheet = hssfWorkbook.getSheetAt(i);
                        Sheet newSheet = xssfWorkbook.createSheet(oldSheet.getSheetName());

                        // 复制每行
                        for (int rowIndex = 0; rowIndex <= oldSheet.getLastRowNum(); rowIndex++) {
                            Row oldRow = oldSheet.getRow(rowIndex);
                            if (oldRow != null) {
                                Row newRow = newSheet.createRow(rowIndex);
                                copyRow(oldRow, newRow, xssfWorkbook);
                            }
                        }
                    }

                    // 写入数据到ByteArrayOutputStream
                    xssfWorkbook.write(byteArrayOutputStream);
                    buf = byteArrayOutputStream.toByteArray();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf;
    }

    private static void copyRow(Row oldRow, Row newRow, XSSFWorkbook newWorkbook) {
        for (int j = 0; j < oldRow.getLastCellNum(); j++) {
            Cell oldCell = oldRow.getCell(j);
            if (oldCell != null) {
                Cell newCell = newRow.createCell(j);
                // 复制单元格数据
                copyCellData(oldCell, newCell);
                // 复制单元格样式
                copyCellStyle(oldCell, newCell, newWorkbook);
            }
        }
    }

    private static void copyCellStyle(Cell oldCell, Cell newCell, Workbook newWorkbook) {
        CellStyle oldStyle = oldCell.getCellStyle();
        CellStyle newStyle = newWorkbook.createCellStyle();
        // 复制字体
        Font oldFont = oldCell.getSheet().getWorkbook().getFontAt(oldStyle.getFontIndex());
        Font newFont = newWorkbook.createFont();
        newFont.setFontName(oldFont.getFontName());
        newFont.setFontHeightInPoints(oldFont.getFontHeightInPoints());
        newFont.setBold(oldFont.getBold());
        // 更多字体属性的复制...
        newStyle.setFont(newFont);

        // 对齐方式
        newStyle.setAlignment(oldStyle.getAlignment());
        newStyle.setVerticalAlignment(oldStyle.getVerticalAlignment());

        // 边框和边框颜色
        newStyle.setBorderTop(oldStyle.getBorderTop());
        newStyle.setBorderRight(oldStyle.getBorderRight());
        newStyle.setBorderBottom(oldStyle.getBorderBottom());
        newStyle.setBorderLeft(oldStyle.getBorderLeft());

        newStyle.setTopBorderColor(oldStyle.getTopBorderColor());
        newStyle.setRightBorderColor(oldStyle.getRightBorderColor());
        newStyle.setBottomBorderColor(oldStyle.getBottomBorderColor());
        newStyle.setLeftBorderColor(oldStyle.getLeftBorderColor());

        // 设置新样式到新单元格
        newCell.setCellStyle(newStyle);
    }


    private static void copyCellData(Cell oldCell, Cell newCell) {
        switch (oldCell.getCellType()) {
            case STRING:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case BLANK:
                newCell.setCellType(CellType.BLANK);
                break;
            default:
                break;
        }
    }
}

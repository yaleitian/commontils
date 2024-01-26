package com.example.demoboot.utils.exceltools;

import com.spire.xls.ExcelVersion;
import com.spire.xls.Worksheet;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class PoiReadSpireWrite {
    public static void main(String[] args) throws IOException {
        // 使用 Apache POI 读取 Excel 文件
        FileInputStream fis = new FileInputStream("/Users/tianyalei/Downloads/2348567新建 XLSX 工作表.xls");
        Workbook poiWorkbook = WorkbookFactory.create(fis);

        // 创建 Spire.XLS 的 Workbook 对象
        com.spire.xls.Workbook spireWorkbook = new com.spire.xls.Workbook();
        spireWorkbook.setVersion(ExcelVersion.Version2013);

        // 遍历 Apache POI Workbook 中的每个 Sheet
        for (int i = 0; i < poiWorkbook.getNumberOfSheets(); i++) {
            Sheet poiSheet = poiWorkbook.getSheetAt(i);
            Worksheet spireSheet = spireWorkbook.getWorksheets().add(poiSheet.getSheetName());

            // 遍历 Apache POI Sheet 中的每一行和每一个单元格
            for (Row row : poiSheet) {
                for (Cell cell : row) {
                    // 获取单元格数据，并写入到 Spire.XLS 的单元格中
                    int rowIndex = cell.getRowIndex();
                    int colIndex = cell.getColumnIndex();
                    CellValue cellValue = getCellValue(cell);
                    // 根据单元格数据类型进行处理
                    switch (cellValue.getCellType()) {
                        case STRING:
                            spireSheet.setText(rowIndex, colIndex, cellValue.getStringValue());
                            break;
                        case NUMERIC:
                            spireSheet.setNumber(rowIndex, colIndex, cellValue.getNumberValue());
                            break;
                        // 可以根据需要添加更多的类型
                    }
                }
            }
        }

        // 保存 Spire.XLS Workbook 到新文件
        spireWorkbook.saveToFile("/Users/tianyalei/Downloads/2348567新建 XLSX 工作表.xlsx", ExcelVersion.Version2013);
    }

    private static CellValue getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return new CellValue(cell.getStringCellValue());
            case NUMERIC:
                return new CellValue(cell.getNumericCellValue());
            // 可以根据需要添加更多的类型
            default:
                return new CellValue("");
        }
    }
}

// 用于表示单元格值的简单类
class CellValue {
    private final String stringValue;
    private final double numberValue;
    private final CellType cellType;

    public CellValue(String value) {
        this.stringValue = value;
        this.numberValue = 0;
        this.cellType = CellType.STRING;
    }

    public CellValue(double value) {
        this.stringValue = null;
        this.numberValue = value;
        this.cellType = CellType.NUMERIC;
    }

    public String getStringValue() {
        return stringValue;
    }

    public double getNumberValue() {
        return numberValue;
    }

    public CellType getCellType() {
        return cellType;
    }
}


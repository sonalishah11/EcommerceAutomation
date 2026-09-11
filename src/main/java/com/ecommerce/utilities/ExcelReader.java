package com.ecommerce.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private static final String FILE_PATH =
            "src/test/resources/testdata/LoginTestData.xlsx";

    public static Object[][] getTestData() {

        Object[][] data = null;

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            int rowCount = sheet.getPhysicalNumberOfRows();
            int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

            data = new Object[rowCount - 1][columnCount];

            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i < rowCount; i++) {

                Row row = sheet.getRow(i);

                for (int j = 0; j < columnCount; j++) {

                    Cell cell = row.getCell(j);

                    data[i - 1][j] = formatter.formatCellValue(cell);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read Excel file: " + FILE_PATH);
        }

        return data;
    }
}
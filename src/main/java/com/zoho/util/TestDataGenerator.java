package com.zoho.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {
    private static final String[] FIRST_NAMES = {
            "John", "Jane", "Michael", "Sarah", "Alex", "Emily", "Chris", "Olivia"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Williams", "Jones", "Garcia", "Miller", "Davis"
    };

    public static List<String[]> readExcelData(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                int columnCount = row.getLastCellNum();
                String[] rowData = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = row.getCell(i).getStringCellValue();
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String generateRandomFirstName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        return firstName;
    }

    public static String generateRandomLasttName() {
        Random random = new Random();
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return lastName;
    }

    public static String generateRandomCompanyName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    public static String generateRandomEmail() {
        return "user_" + generateRandomFirstName() + "@example.com";
    }
}

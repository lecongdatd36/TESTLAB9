package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    private static final String FILE_NAME = "login_data.xlsx";

    public static List<Object[]> getData(String sheetName) {
        List<Object[]> data = new ArrayList<>();

        try (Workbook workbook = openWorkbook()) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet '" + sheetName + "' not found in " + getSourceDescription()
                                + ". Available sheets: " + getSheetNames(workbook));
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String user = getCellValue(row.getCell(0));
                String pass = getCellValue(row.getCell(1));
                String expected = getCellValue(row.getCell(2));
                String desc = getCellValue(row.getCell(3));

                if (user.isBlank() && pass.isBlank() && expected.isBlank() && desc.isBlank()) {
                    continue;
                }

                data.add(new Object[] { user, pass, expected, desc });
            }

            if (data.isEmpty()) {
                throw new IllegalStateException(
                        "No data rows found in sheet '" + sheetName + "' of " + getSourceDescription()
                                + ". Add at least one non-empty row after header.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read login test data from Excel: " + e.getMessage(), e);
        }

        return data;
    }

    private static Workbook openWorkbook() throws Exception {
        for (Path candidate : candidatePaths().keySet()) {
            if (Files.exists(candidate)) {
                return WorkbookFactory.create(new FileInputStream(candidate.toFile()));
            }
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            InputStream cpStream = classLoader.getResourceAsStream(FILE_NAME);
            if (cpStream != null) {
                return WorkbookFactory.create(cpStream);
            }
        }

        throw new IllegalStateException(
                "Cannot find " + FILE_NAME + ". Tried classpath and paths: " + candidatePaths().values());
    }

    private static String getSourceDescription() {
        for (Map.Entry<Path, String> entry : candidatePaths().entrySet()) {
            if (Files.exists(entry.getKey())) {
                return entry.getValue();
            }
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null && classLoader.getResource(FILE_NAME) != null) {
            return "classpath:" + FILE_NAME;
        }

        return FILE_NAME;
    }

    private static Map<Path, String> candidatePaths() {
        Map<Path, String> paths = new LinkedHashMap<>();
        paths.put(Paths.get("src", "test", "java", "resources", FILE_NAME), "src/test/java/resources/" + FILE_NAME);
        paths.put(Paths.get("src", "test", "resources", FILE_NAME), "src/test/resources/" + FILE_NAME);
        paths.put(Paths.get(FILE_NAME), FILE_NAME);
        paths.put(Paths.get("..", FILE_NAME), "../" + FILE_NAME);
        return paths;
    }

    private static List<String> getSheetNames(Workbook workbook) {
        if (workbook == null || workbook.getNumberOfSheets() == 0) {
            return Collections.emptyList();
        }

        List<String> names = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            names.add(workbook.getSheetName(i));
        }
        return names;
    }

    // Handle common cell types and null safely.
    public static String getCellValue(Cell cell) {
        if (cell == null)
            return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }
}
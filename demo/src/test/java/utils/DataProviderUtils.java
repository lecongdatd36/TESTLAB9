package utils;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DataProviderUtils {

    private static final String SMOKE_SHEET = "SmokeCases";
    private static final String NEGATIVE_SHEET = "NegativeCases";
    private static final String BOUNDARY_SHEET = "BoundaryCases";

    @DataProvider(name = "smokeData")
    public static Object[][] getSmokeData() {
        return convert(ExcelReader.getData(SMOKE_SHEET));
    }

    @DataProvider(name = "allData")
    public static Object[][] getAllData() {
        List<Object[]> all = new ArrayList<>();
        all.addAll(ExcelReader.getData(SMOKE_SHEET));
        all.addAll(ExcelReader.getData(NEGATIVE_SHEET));
        all.addAll(ExcelReader.getData(BOUNDARY_SHEET));
        return convert(all);
    }

    private static Object[][] convert(List<Object[]> list) {
        Object[][] data = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);
        }
        return data;
    }

    @DataProvider(name = "jsonData")
    public static Object[][] getJsonData() {
        List<UserData> users = JsonReader.readUsers();

        Object[][] data = new Object[users.size()][1];

        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i);
        }

        return data;
    }
}
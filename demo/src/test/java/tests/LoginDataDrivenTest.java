package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginDataDrivenTest extends BaseTest implements ITest {

    private String testName = "";

    @Override
    public String getTestName() {
        return testName;
    }

    @Test(dataProvider = "smokeData", dataProviderClass = utils.DataProviderUtils.class, groups = "smoke")
    public void testLoginSmoke(String user, String pass, String expected, String desc) {
        testName = desc;

        LoginPage loginPage = new LoginPage(getDriver());
        boolean result = loginPage.login(user, pass)
                .isLoaded();

        Assert.assertTrue(result, desc);
    }

    @Test(dataProvider = "allData", dataProviderClass = utils.DataProviderUtils.class, groups = "regression")
    public void testLoginAll(String user, String pass, String expected, String desc) {
        testName = desc;

        LoginPage loginPage = new LoginPage(getDriver());

        String normalizedExpected = expected == null ? "" : expected.trim().toLowerCase();

        if (normalizedExpected.contains("inventory")) {
            boolean result = loginPage.login(user, pass)
                    .isLoaded();
            Assert.assertTrue(result, desc);
        } else {
            LoginPage failedPage = loginPage.loginExpectingFailure(user, pass);
            String error = failedPage.getErrorMessage();

            if ("error".equals(normalizedExpected)) {
                Assert.assertTrue(failedPage.isErrorDisplayed() && !error.isBlank(), desc);
            } else {
                Assert.assertTrue(error.contains(expected), desc);
            }
        }
    }
}
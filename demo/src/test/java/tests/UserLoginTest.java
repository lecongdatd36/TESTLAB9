package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.UserData;

public class UserLoginTest extends BaseTest implements ITest {

    private String testName = "";

    @Override
    public String getTestName() {
        return testName;
    }

    @Test(dataProvider = "jsonData", dataProviderClass = utils.DataProviderUtils.class)
    public void testLoginJson(UserData user) {

        testName = user.getDescription();

        LoginPage loginPage = new LoginPage(getDriver());

        if (user.isExpectSuccess()) {
            boolean result = loginPage.login(user.getUsername(), user.getPassword())
                                     .isLoaded();
            Assert.assertTrue(result, testName);
        } else {
            boolean error = loginPage
                    .loginExpectingFailure(user.getUsername(), user.getPassword())
                    .isErrorDisplayed();

            Assert.assertTrue(error, testName);
        }
    }
}
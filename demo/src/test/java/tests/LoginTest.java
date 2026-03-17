package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void test1() {
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertTrue(true);
    }

    @Test
    public void testFail() {
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertEquals("A", "B"); // cố tình fail
    }
}
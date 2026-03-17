package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import pages.LoginPage;
import utils.TestDataFactory;

import java.util.Map;

public class CheckoutTest extends BaseTest {

    @Test
    public void testCheckoutRandom() {

        Map<String, String> data = TestDataFactory.randomCheckoutData();

        System.out.println("FirstName: " + data.get("firstName"));
        System.out.println("LastName: " + data.get("lastName"));
        System.out.println("PostalCode: " + data.get("postalCode"));

        CheckoutPage checkoutPage = new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart()
                .goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(), "Checkout information page should be loaded");

        checkoutPage.fillCheckoutInfo(
                data.get("firstName"),
                data.get("lastName"),
                data.get("postalCode"));
    }
}
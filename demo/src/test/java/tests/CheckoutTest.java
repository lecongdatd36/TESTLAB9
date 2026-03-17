package tests;

import base.BaseTest;
import org.testng.annotations.Test;
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

        new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart()
                .goToCheckout();
    }
}
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By postalCodeInput = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By checkoutInfoContainer = By.id("checkout_info_container");
    private By checkoutSummaryContainer = By.id("checkout_summary_container");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(checkoutInfoContainer);
    }

    public CheckoutPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        waitAndType(firstNameInput, firstName);
        waitAndType(lastNameInput, lastName);
        waitAndType(postalCodeInput, postalCode);
        waitAndClick(continueButton);
        return this;
    }

    public boolean isOverviewLoaded() {
        return isElementVisible(checkoutSummaryContainer);
    }
}
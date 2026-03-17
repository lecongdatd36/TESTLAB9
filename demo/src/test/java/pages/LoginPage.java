package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");
    private By errorMsg = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Login thành công
     * 
     * @param user username
     * @param pass password
     * @return InventoryPage (trang sau khi login)
     */
    public InventoryPage login(String user, String pass) {
        waitAndType(username, user);
        waitAndType(password, pass);
        waitAndClick(loginBtn);
        return new InventoryPage(driver);
    }

    /**
     * Login thất bại (vẫn ở LoginPage)
     * 
     * @param user
     * @param pass
     * @return
     */
    public LoginPage loginExpectingFailure(String user, String pass) {
        waitAndType(username, user);
        waitAndType(password, pass);
        waitAndClick(loginBtn);
        return this;
    }

    /**
     * Lấy thông báo lỗi
     * 
     * @return String error message
     */
    public String getErrorMessage() {
        return getText(errorMsg);
    }

    /**
     * Kiểm tra error có hiển thị không
     * 
     * @return true nếu hiển thị
     */
    public boolean isErrorDisplayed() {
        return isElementVisible(errorMsg);
    }

}
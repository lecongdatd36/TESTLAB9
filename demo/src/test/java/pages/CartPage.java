package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object cho trang Cart
 */
public class CartPage extends BasePage {

    // ===== LOCATOR =====
    private By cartItems = By.className("cart_item");
    private By itemNames = By.className("inventory_item_name");
    private By removeButtons = By.xpath("//button[contains(text(),'Remove')]");
    private By checkoutBtn = By.id("checkout");

    // ===== CONSTRUCTOR =====
    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Lấy số lượng item trong giỏ
     * Nếu không có → trả về 0
     */
    public int getItemCount() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size(); // nếu rỗng → = 0 luôn (đúng yêu cầu)
    }

    /**
     * Xóa item đầu tiên trong giỏ
     */
    public CartPage removeFirstItem() {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
        return this;
    }

    /**
     * Đi tới trang Checkout
     */
    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutBtn);
        return new CheckoutPage(driver);
    }

    /**
     * Lấy danh sách tên sản phẩm trong giỏ
     */
    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> elements = driver.findElements(itemNames);

        for (WebElement el : elements) {
            names.add(el.getText());
        }

        return names;
    }
}
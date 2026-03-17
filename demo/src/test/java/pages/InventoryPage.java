package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object cho trang Inventory (sau khi login)
 */
public class InventoryPage extends BasePage {

    // ===== LOCATOR =====
    private By inventoryList = By.id("inventory_container");
    private By productItems = By.className("inventory_item");
    private By productNames = By.className("inventory_item_name");
    private By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartIcon = By.id("shopping_cart_container");

    // ===== CONSTRUCTOR =====
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Kiểm tra trang đã load chưa
     */
    public boolean isLoaded() {
        return isElementVisible(inventoryList);
    }

    /**
     * Thêm sản phẩm đầu tiên vào giỏ
     */
    public InventoryPage addFirstItemToCart() {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
        return this;
    }

    /**
     * Thêm sản phẩm theo tên
     */
    public InventoryPage addItemByName(String name) {
        List<WebElement> items = driver.findElements(productItems);

        for (WebElement item : items) {
            String productName = item.findElement(productNames).getText();

            if (productName.equalsIgnoreCase(name)) {
                item.findElement(By.tagName("button")).click();
                break;
            }
        }
        return this;
    }

    /**
     * Lấy số lượng item trong cart
     */
    public int getCartItemCount() {
        try {
            String count = getText(cartBadge);
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0; // không có item
        }
    }

    /**
     * Đi tới trang Cart
     */
    public CartPage goToCart() {
        waitAndClick(cartIcon);
        return new CartPage(driver);
    }
}
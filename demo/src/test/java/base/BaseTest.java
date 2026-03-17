package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BaseTest {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    
    @Parameters({"browser", "env"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser,
                      @Optional("dev") String env) {

        WebDriver webDriver;

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();
        } else {
            throw new RuntimeException("Browser không hỗ trợ: " + browser);
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.set(webDriver);

    
        if (env.equals("dev")) {
            getDriver().get("https://www.saucedemo.com/");
        }
    }

 
    public WebDriver getDriver() {
        return driver.get();
    }

    
    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }

        if (getDriver() != null) {
            getDriver().quit();
        }
    }

   
    private void takeScreenshot(String testName) {
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String path = "target/screenshots/" + testName + "_" + timestamp + ".png";

        try {
            File dest = new File(path);
            dest.getParentFile().mkdirs(); // tạo folder nếu chưa có
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
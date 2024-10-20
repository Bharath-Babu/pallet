package com.zoho.webPages;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.zoho.util.ConfigReader;
import com.zoho.util.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;


public class BasePage {
    public static WebDriver driver;
    public static ExtentReports extent;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static String captureScreenShotBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext context) {
        ConfigReader.loadProperties("src/test/resources/config.properties");
        String browser = ConfigReader.getProperty("browser");
        driver = WebDriverFactory.createDriver(browser);
        context.setAttribute("WebDriver", driver);
        driver.get(ConfigReader.getProperty("baseURL"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public Media captureScreenShot() {
        String base64Screenshot = captureScreenShotBase64(driver); // Capture Base64 screenshot
        return MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build(); // Return the media entity
    }


}

package com.zoho.webPages;

import com.zoho.util.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends BasePage {
    WebDriver driver;

    @FindBy(xpath = "//a[text()='Sign In']")
    private WebElement signInButton;

    @FindBy(id = "login_id")
    private WebElement emailField;

    @FindBy(id = "nextbtn")
    private WebElement nextButton;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "nextbtn")
    private WebElement logInButton;

    @FindBy(id ="show-uName")
    private WebElement welcomeHome;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login() {
        signInButton.click();
        extentTest.get().info("Clicking 'Sign In' button in home Page", captureScreenShot());
        emailField.sendKeys(ConfigReader.getProperty("username"));
        extentTest.get().info("Entering Email Id", captureScreenShot());
        nextButton.click();
        extentTest.get().info("Click on Next Button", captureScreenShot());
        passwordField.sendKeys(ConfigReader.getProperty("password"));
        extentTest.get().info("Entering password", captureScreenShot());
        nextButton.click();
        extentTest.get().info("Click on Sign Button", captureScreenShot());
    }

    public boolean verifyHomePage() {
        welcomeHome.isDisplayed();
        return driver.getCurrentUrl().contains("Home/begin");

    }

}

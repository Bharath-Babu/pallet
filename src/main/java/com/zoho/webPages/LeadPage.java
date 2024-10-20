package com.zoho.webPages;


import com.zoho.enums.LeadFormField;
import com.zoho.enums.Salutation;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;
import java.util.NoSuchElementException;


public class LeadPage extends BasePage {

    @FindBy(xpath = "//div[@data-value='Leads']")
    private WebElement leadsTab;

    @FindBy(xpath = "//button[text()='Create Lead']")
    private WebElement createLeadButton;

    @FindBy(id = "crm_create_savebutn")
    private WebElement saveLeadButton;

    @FindBy(xpath = "//div[@aria-label='Salutation' and .//span[text()='-None-']]")
    private WebElement clickSalutation;

    @FindBy(xpath = "//span[contains(@id,'errorMsg_Crm_Leads_')]")
    private List<WebElement> checkErrorItem;

    @FindBy(xpath = "//lyte-yield[text()='Edit']")
    private WebElement editButton;

    @FindBy(xpath = "//lyte-yield/child::a/lyte-text")
    private List<WebElement> listOfLeadName;

    @FindBy(xpath = "//button[@aria-label='More Options']")
    private WebElement moreOptions;

    @FindBy(xpath = "//lyte-menu-item[@data-zcqa='delete']")
    private WebElement deleteLead;

    @FindBy(xpath = "//lyte-yield[text()='Delete']/parent::button[@type='accept']")
    private WebElement acceptDeleteLead;

    @FindBy(xpath = "//input[@type='text']/ancestor::lyte-input[@lt-prop-id='inputId']")
    private WebElement filterInputSearchBox;

    @FindBy(xpath = "//lyte-yield[text()='Apply Filter']")
    private WebElement applyFilter;

    @FindBy(xpath = "//lyte-yield[text()='OK, got it!']")
    private WebElement okPopUp;

    public LeadPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private WebElement getLeadFormField(int tabIndex) {
        return driver.findElement(By.xpath("//input[contains(@type, 'text') and @tabindex='" + tabIndex + "']"));
    }

    private WebElement getLeadName(String name) {
        return driver.findElement(By.xpath("//lyte-yield/child::a[@data-zcqa='" + name + "']"));
    }

    private WebElement getSalutationValue(String salutation) {
        return driver.findElement(By.xpath("//lyte-drop-item[@data-value='" + salutation + "']"));
    }

    private WebElement getCheckbox(String filterTitle) {
        return driver.findElement(By.xpath("//lyte-checkbox[@title='" + filterTitle + "']//input"));
    }

    private WebElement getCheckboxForName(String name) {
        return driver.findElement(By.xpath("//input[@type='text']/ancestor::lyte-input[@id='id_" + name + "_Name']"));
    }

    public void clickOnLeads() {
        leadsTab.click();
        extentTest.get().info("Clicked on Lead Tab", captureScreenShot());
    }

    public void createNewLead(Map<String, String> data) {
        clickOnLeads();
        createLeadButton.click();
        extentTest.get().info("Clicked on Create Lead Button", captureScreenShot());
        clickSalutation.click();
        getSalutationValue(Salutation.MR.getPrefix()).click();
        extentTest.get().info("Select salutation", captureScreenShot());
        getLeadFormField(LeadFormField.FIRST_NAME.getTabIndex()).sendKeys(data.get("FirstName"));
        getLeadFormField(LeadFormField.COMPANY.getTabIndex()).sendKeys(data.get("CompanyName"));
        getLeadFormField(LeadFormField.LAST_NAME.getTabIndex()).sendKeys(data.get("LastName"));
        getLeadFormField(LeadFormField.EMAIL.getTabIndex()).sendKeys(data.get("Email"));
        if (checkMandatoryField())
            extentTest.get().info("No Mandatory Errors", captureScreenShot());
        else
            extentTest.get().fail("Please enter Mandatory Field", captureScreenShot());
        saveLeadButton.click();
        extentTest.get().info("Click on Save Lead Buttonn", captureScreenShot());
    }

    public boolean checkMandatoryField() {
        return checkErrorItem.isEmpty();
    }

    public String getRandomLeadName() {
        int totalLeads = listOfLeadName.size();
        Random random = new Random();
        String leadName = null;
        if (totalLeads > 1) {
            int randomIndex = random.nextInt(totalLeads);
            leadName = listOfLeadName.get(randomIndex).getText();
            extentTest.get().info("Getting Random Lead Name", captureScreenShot());
        } else {
            extentTest.get().info("Not enough leads available to Get a random Name.", captureScreenShot());
        }
        return leadName;
    }

    public void selectLeadNametoEdit(String updatedName) {
        getLeadName(updatedName).click();
        extentTest.get().info("Selected given Lead Name to Edit", captureScreenShot());

    }

    public void editLead(String updatedName, String fieldTobeUpdated) {
        editButton.click();
        getLeadFormField(LeadFormField.valueOf(fieldTobeUpdated).getTabIndex()).clear();
        getLeadFormField(LeadFormField.valueOf(fieldTobeUpdated).getTabIndex()).sendKeys(updatedName);
        extentTest.get().info("Updated" + fieldTobeUpdated, captureScreenShot());
        saveLeadButton.click();
        extentTest.get().info("Click on Save Lead Button", captureScreenShot());

    }

    public void deleteLead() {
        moreOptions.click();
        extentTest.get().info("Click on More Options", captureScreenShot());
        deleteLead.click();
        extentTest.get().info("Click on Delete", captureScreenShot());
        acceptDeleteLead.click();
        extentTest.get().info("click on Delete Confirm Button", captureScreenShot());
    }

    public void searchByFilterSearchBox(String typeSearch) {
        filterInputSearchBox.sendKeys(typeSearch);
        extentTest.get().info("Filtering by the text" + typeSearch, captureScreenShot());
    }


    public void selectFilterByNameCheckBox(String filterByDesiredOption) {
        try {
            if (!getCheckbox(filterByDesiredOption).isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getCheckbox(filterByDesiredOption));
                extentTest.get().info(filterByDesiredOption + " CheckBox is Selected", captureScreenShot());
            } else {
                extentTest.get().info(filterByDesiredOption + " CheckBox already Selected", captureScreenShot());
            }
        } catch (NoSuchElementException e) {
            extentTest.get().info("Filter with title '" + filterByDesiredOption + "' not found.", captureScreenShot());
        }
    }

    public void sendValueAndApplyFilter(String searchByText, String searchByTextValue) {
        getCheckboxForName(searchByText).sendKeys(searchByTextValue);
        applyFilter.click();
    }
}

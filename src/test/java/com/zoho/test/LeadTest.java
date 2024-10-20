package com.zoho.test;

import com.zoho.util.TestDataGenerator;
import com.zoho.webPages.BasePage;
import com.zoho.webPages.LeadPage;
import com.zoho.webPages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.Map;

public class LeadTest extends BasePage {
    String firstName = TestDataGenerator.generateRandomFirstName();
    String lastName = TestDataGenerator.generateRandomLasttName();
    String companyName = TestDataGenerator.generateRandomCompanyName();
    String email = TestDataGenerator.generateRandomEmail();
    private LoginPage loginPage;
    private LeadPage leadPage;

    @BeforeMethod
    public void pageSetup() {
        loginPage = new LoginPage(driver);
        leadPage = new LeadPage(driver);

    }


    @Test
    public void testCreateNewLead() {

        Map<String, String> data = new HashMap<>();
        data.put("FirstName", firstName);
        data.put("LastName", lastName);
        data.put("CompanyName", companyName);
        data.put("Email", email);
        loginPage.login();
        if (loginPage.verifyHomePage())
            extentTest.get().pass("Login Successfull", captureScreenShot());
        else
            extentTest.get().fail("Login UnSuccessfull", captureScreenShot());

        leadPage.createNewLead(data);
        Assert.assertTrue(driver.getPageSource().contains(firstName));
        extentTest.get().pass("Lead Creation Successfull", captureScreenShot());
    }

    @Test
    public void testEditLead() {
        String nameTobeEdited;
        loginPage.login();
        if (loginPage.verifyHomePage())
            extentTest.get().pass("Login Successfull", captureScreenShot());
        else
            extentTest.get().fail("Login UnSuccessfull", captureScreenShot());
        leadPage.clickOnLeads();
        nameTobeEdited = leadPage.getRandomLeadName();
        leadPage.selectLeadNametoEdit(nameTobeEdited);
        Assert.assertTrue(driver.getPageSource().contains(nameTobeEdited));
        leadPage.editLead(firstName + "@user.com", "EMAIL");
        Assert.assertTrue(driver.getPageSource().contains(firstName + "@user.com"));
    }

    @Test
    public void testFilterLeads() {
        String searchByFirstNameValue;
        String selectFilterOption = "First Name";
        loginPage.login();
        if (loginPage.verifyHomePage())
            extentTest.get().pass("Login Successfull", captureScreenShot());
        else
            extentTest.get().fail("Login UnSuccessfull", captureScreenShot());
        leadPage.clickOnLeads();
        searchByFirstNameValue = leadPage.getRandomLeadName().split(" ")[0];
        leadPage.searchByFilterSearchBox("First");
        leadPage.selectFilterByNameCheckBox(selectFilterOption);
        leadPage.sendValueAndApplyFilter(selectFilterOption.split(" ")[0], searchByFirstNameValue);
        Assert.assertTrue(driver.getPageSource().contains(searchByFirstNameValue));
        extentTest.get().pass("Filtering  is Successfull", captureScreenShot());
    }

    @Test
    public void testDeleteLead() {
        String nameTobeEdited;
        loginPage.login();
        if (loginPage.verifyHomePage())
            extentTest.get().pass("Login Successful", captureScreenShot());
        else
            extentTest.get().fail("Login UnSuccessfull", captureScreenShot());
        leadPage.clickOnLeads();
        nameTobeEdited = leadPage.getRandomLeadName();
        leadPage.selectLeadNametoEdit(nameTobeEdited);
        leadPage.deleteLead();
    }


}

package com.zoho.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.zoho.webPages.BasePage;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtentReportListener extends BasePage implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // Timestamp
        String reportFile = System.getProperty("user.dir") + "/reports/ExtentReport_" + timeStamp + ".html"; // Dynamic name

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportFile);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    // Start a new test for each test method and append the results
    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test in the report for each method
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log the success status in the report
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log the failure status and the reason for failure
        extentTest.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        extentTest.get().fail(result.getThrowable());  // Log exception
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log the skip status
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Write all the logs to the report
        if (extent != null) {
            extent.flush();
        }
    }
}

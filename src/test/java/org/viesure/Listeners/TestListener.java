package org.viesure.Listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.viesure.page.base.BaseTest;

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG (WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog (String message) {
        return message;
    }


    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("onTestStart method for" +  getTestMethodName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess method for " +  getTestMethodName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFailure method for " +  getTestMethodName(result));

        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        //Save screenshot to allure
        if (driver != null) {
            System.out.println("Screenshot captured for test case:" + getTestMethodName(result));
            saveScreenshotPNG(driver);
        }

        //Save a log on allure.
        saveTextLog(getTestMethodName(result) + " failed and screenshot taken!");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("onTestSkipped method for "+  getTestMethodName(result));
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("onStart method for " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("onFinish method for " + context.getName());
    }
}

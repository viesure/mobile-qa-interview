package org.viesure;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    AppiumDriver<?> driver;

    @BeforeClass
    public void beforeClass(){

        File app = new File(System.getProperty("user.dir") + "/app/android/" + "qa-interview.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP,app.getAbsolutePath());
        capabilities.setCapability("deviceName","Pixel XL API 30");
        capabilities.setCapability("udid","emulator-5554");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appPackage","io.viesure.qa");
        capabilities.setCapability("appActivity","io.viesure.qa.views.MainActivity");
//        capabilities.setCapability("platformVersion","");

        try {
            driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }
}

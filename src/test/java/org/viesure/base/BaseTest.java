package org.viesure.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.viesure.Listeners.TestListener;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Listeners(TestListener.class)
public class BaseTest {

    protected AndroidDriver<AndroidElement> driver;

    File app = new File(System.getProperty("user.dir") + "/app/android/" + "qa-interview.apk");

    @BeforeClass
    public void beforeClass(){

        initalizeAndroidDriver();
    }

    @AfterClass
    public void afterClass(){
        if (driver != null) {
            driver.quit();
        }
    }

    private void initalizeAndroidDriver(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP,app.getAbsolutePath());
        capabilities.setCapability("deviceName","Pixel XL API 30");
        capabilities.setCapability("udid","emulator-5554");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appPackage","io.viesure.qa");
        capabilities.setCapability("appActivity","io.viesure.qa.views.MainActivity");

        try {
            driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Step("Navigating back using android system's back button")
    public void navigateBack(){
        driver.navigate().back();
    }

    public AndroidDriver<?> getDriver(){
        return this.driver;
    }
}

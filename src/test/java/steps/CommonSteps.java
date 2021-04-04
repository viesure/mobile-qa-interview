package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class CommonSteps {
    private AndroidDriver<AndroidElement> driver;
    private File app = new File(System.getProperty("user.dir") + "/app/android/" + "qa-interview.apk");

    @Before(order = 0)
    public void setUp(){
        initalizeAndroidDriver();
    }

    @After(order = 1)
    public void tearDown(){
        driver.quit();
    }

    public AndroidDriver<AndroidElement> getDriver(){
        return driver;
    }

    public void initalizeAndroidDriver(){
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
}

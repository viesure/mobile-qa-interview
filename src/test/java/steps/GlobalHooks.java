package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class GlobalHooks {
    private AndroidDriver<AndroidElement> driver;
    private File app = new File(System.getProperty("user.dir") + "/app/android/" + "qa-interview.apk");

    @Before(order = 0)
    @Step("Initalizing Android driver")
    public void setUp(){
        initalizeAndroidDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario){
        if (scenario.isFailed()){
            saveTextLog("Saved screenshot for failed step: " + scenario.getName());
            saveScreenshotPNG();

        }
    }

    @After(order = 1)
    @Step("Quitting android driver")
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
//        capabilities.setCapability("deviceName","Nexus S API 24");
        capabilities.setCapability("udid","emulator-5554");
//        capabilities.setCapability("udid","emulator-5556");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appPackage","io.viesure.qa");
        capabilities.setCapability("appActivity","io.viesure.qa.views.MainActivity");

        try {
//            driver = new AndroidDriver<>(new URL("http://0.0.0.0:4728/wd/hub"), capabilities);
            driver = new AndroidDriver<>(new URL("http://0.0.0.0:4755/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Step("Navigating back using android system's back button")
    public void navigateBack(){
        driver.navigate().back();
    }

    @Attachment(value = "Click to open issue screenshot", type = "image/png")
    public byte[] saveScreenshotPNG () {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog (String message) {
        return message;
    }
}

package steps;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.viesure.utils.AllureLogger;
import org.viesure.utils.PropertyFileLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class GlobalHooks {
    private File app = new File(System.getProperty("user.dir") + "/app/android/" + "qa-interview.apk");

    private static ThreadLocal<AndroidDriver<AndroidElement>> threadedDriver = new ThreadLocal<>();


    @Before(order = 0)
    @Step("Initalizing Android driver")
    public void setUp(){
        initalizeAndroidDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario){
        if (scenario.isFailed()){
            AllureLogger.saveTextLog("Saved screenshot for failed step: " + scenario.getName());
            AllureLogger.saveScreenshotPNG(threadedDriver.get());
        }
    }

    @After(order = 1)
    @Step("Quitting android driver")
    public void tearDown(){
        threadedDriver.get().quit();
    }

    public AndroidDriver<AndroidElement> getDriver(){
        return threadedDriver.get();
    }

    public void initalizeAndroidDriver(){
        //Getting the device parameter from testNG
        String device = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("device");

        //If we dont get a device name, we are defaulting for pixel for now
        if (device == null){
            device = "pixel";
        }

        initalizeDriverFor(device);
    }

    @Step("Navigating back using android system's back button")
    public void navigateBack(){
        threadedDriver.get().navigate().back();
    }

    public void initalizeDriverFor(String device){
        Properties properties = PropertyFileLoader.loadDeviceProperty(device);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP,app.getAbsolutePath());
        capabilities.setCapability("deviceName",properties.getProperty("deviceName"));
        capabilities.setCapability("udid",properties.getProperty("udid"));
        capabilities.setCapability("platformName",properties.getProperty("platformName"));
        capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
        capabilities.setCapability("appPackage","io.viesure.qa");
        capabilities.setCapability("appActivity","io.viesure.qa.views.MainActivity");

        try {
            threadedDriver.set(new AndroidDriver<>(new URL(properties.getProperty("remoteUrl")), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}

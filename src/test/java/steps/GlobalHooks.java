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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

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
        String device = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("device");
        if (device == null){
            device = "pixel";
        }
        System.out.println("Device is: " + device);
        DesiredCapabilities capabilities;
        if (device.equals("pixel")){
            capabilities = getPixelCapabilities();

//            try {
//                driver = new AndroidDriver<>(new URL("http://0.0.0.0:4755/wd/hub"), capabilities);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
        } else {
            capabilities = getNexusCapabilities();

//            try {
//                driver = new AndroidDriver<>(new URL("http://0.0.0.0:4728/wd/hub"), capabilities);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
        }

    }

    @Step("Navigating back using android system's back button")
    public void navigateBack(){
        threadedDriver.get().navigate().back();
    }


    public DesiredCapabilities getPixelCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP,app.getAbsolutePath());
        capabilities.setCapability("deviceName","Pixel XL API 30");
        capabilities.setCapability("udid","emulator-5554");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
        capabilities.setCapability("appPackage","io.viesure.qa");
        capabilities.setCapability("appActivity","io.viesure.qa.views.MainActivity");

        try {
            threadedDriver.set(new AndroidDriver<>(new URL("http://0.0.0.0:4755/wd/hub"), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return capabilities;
    }

    public DesiredCapabilities getNexusCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP,app.getAbsolutePath());
        capabilities.setCapability("deviceName","Nexus S API 24");
        capabilities.setCapability("udid","emulator-5556");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
        capabilities.setCapability("appPackage","io.viesure.qa");
        capabilities.setCapability("appActivity","io.viesure.qa.views.MainActivity");

        try {
            threadedDriver.set(new AndroidDriver<>(new URL("http://0.0.0.0:4728/wd/hub"), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return capabilities;
    }

}

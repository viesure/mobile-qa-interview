package org.viesure.base;

import io.appium.java_client.TouchAction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;


public class Gestures {

    public static void scrollDown(AndroidDriver<AndroidElement> driver){
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        (new TouchAction<>(driver)).press(PointOption.point(width/2,height/2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(width/2,100))
                .release().perform();

    }

    public static void scrollUp(AndroidDriver<AndroidElement> driver){
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        (new TouchAction<>(driver)).press(PointOption.point(width/2,height/2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(width/2,height-100))
                .release().perform();
    }
}

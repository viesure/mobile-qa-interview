package org.viesure.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class BasePage {

    protected final AndroidDriver<AndroidElement> driver;

    public BasePage(AndroidDriver<AndroidElement> driver){
        this.driver = driver;
    }
}

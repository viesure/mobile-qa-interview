package org.viesure.base;

import io.appium.java_client.AppiumDriver;

public class BasePage {

    protected final AppiumDriver<?> driver;

    public BasePage(AppiumDriver<?> driver){
        this.driver = driver;
    }
}

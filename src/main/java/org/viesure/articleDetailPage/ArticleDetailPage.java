package org.viesure.articleDetailPage;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.viesure.base.BasePage;


public class ArticleDetailPage extends BasePage {

    ArticleDetailPageLocators locators;

    public ArticleDetailPage(AndroidDriver<AndroidElement> driver){
        super(driver);

        locators = new ArticleDetailPageLocators(driver);

        WebDriverWait wait = new WebDriverWait(this.driver, 10);
        wait.until(ExpectedConditions.visibilityOf(locators.articleDescription));
    }

    @Step("Clicking on top back button")
    public void clickBackButton(){
        locators.backButton.click();
    }

    @Step("Clicking on share button")
    public void clickShareButton(){
        locators.shareButton.click();
    }

    public String getPageTitle(){
        return locators.pageTitle.getText();
    }

    public String getCreatedTime(){
        return locators.createdTime.getText();
    }

    public String getTitle(){
        return locators.articleTitle.getText();
    }

    public String getDescription(){
        return locators.articleDescription.getText();
    }

    @Step("Trying to get author of the detail page")
    public String getAuthor(){
        MobileElement element = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().textContains(\"author\"))"));
        return element.getText();
    }


}
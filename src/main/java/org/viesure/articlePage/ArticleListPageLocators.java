package org.viesure.articlePage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class ArticleListPageLocators {

    @AndroidFindBy(id = "io.viesure.qa:id/action_bar_root")
    List<WebElement> action_bar_root;

    @AndroidFindBy(id = "android:id/content")
    List<WebElement> content;

    @AndroidFindBy(xpath = "//android.view.ViewGroup/android.view.View/android.view.View")
    List<WebElement> articles;

    public ArticleListPageLocators(AppiumDriver<?> driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }
}

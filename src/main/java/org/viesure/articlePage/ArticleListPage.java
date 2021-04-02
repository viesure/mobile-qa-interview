package org.viesure.articlePage;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.viesure.base.BasePage;

import java.util.ArrayList;
import java.util.List;


public class ArticleListPage extends BasePage {

    private ArticleListPageLocators locators;
    private List<ArticleElement> visibleArticleList;

    public ArticleListPage(AppiumDriver<?> driver){
        super(driver);

        locators = new ArticleListPageLocators(driver);

        waitUntilPageIsPopulated();
        getVisibleArticles();
    }

    public List<ArticleElement> getVisibleArticles(){
        generateVisibleArticleList();
        return visibleArticleList;
    }

    private void waitUntilPageIsPopulated(){
        WebDriverWait wait = new WebDriverWait(this.driver, 10);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//android.view.ViewGroup/android.view.View/android.view.View"),1));
    }

    private void generateVisibleArticleList(){
        //refreshing because if we scroll away, locators might changed
        visibleArticleList = new ArrayList<>();
        for (WebElement article: locators.articles){
            if (!article.getText().contains("Dummy articles")){
                this.visibleArticleList.add(new ArticleElement(article));
            }
        }
    }
}

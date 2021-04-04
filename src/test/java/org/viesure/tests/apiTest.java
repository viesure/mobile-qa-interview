package org.viesure.tests;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.base.BaseTest;
import org.viesure.base.Gestures;
import org.viesure.page.common.Article;
import org.viesure.page.utils.Networking;

import java.util.List;

public class apiTest extends BaseTest {

    private List<Article> articles;

    //Expectation data
    private final int expectedArticleListSize = 60;

    @Test
    public void loadArticlesFromBackend(){
        articles= Networking.getDummyData();

        Assert.assertEquals(articles.size(), expectedArticleListSize);
    }

    @Test(dependsOnMethods = "loadArticlesFromBackend")
    public void test(){
        for (Article article: articles){
            List<AndroidElement> elements = driver.findElements(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().textContains(\""+ article.getTitle() + "\"))"));

            if (elements.isEmpty()){
                Gestures.scrollDown(driver);

                elements = driver.findElements(MobileBy.AndroidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().textContains(\""+ article.getTitle() + "\"))"));
            }

            elements.get(0).click();
            assertDetailPage(article);

        }
        System.out.println("Tested all articles");
    }

    private void assertDetailPage(Article article){
        ArticleDetailPage detailPage = new ArticleDetailPage(driver);

        Assert.assertEquals(detailPage.getPageTitle(), article.getTitle(), "Asserting page title");
        Assert.assertEquals(detailPage.getTitle(), article.getTitle(), "Asserting description title");
        Assert.assertEquals(detailPage.getCreatedTime(), article.getRelease_date(), "Asserting release date");
        Assert.assertEquals(detailPage.getDescription(), article.getDescription(), "Asserting description");
        Assert.assertEquals(detailPage.getAuthor(), "author: " + article.getAuthor(), "Asserting author");

        detailPage.clickBackButton();
    }

    public boolean isElementPresent(MobileElement element, int timeoutInSeconds){
        try{
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch(Exception e){
            return false;
        }
    }
}

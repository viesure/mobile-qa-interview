package org.viesure.articlePage;

import io.appium.java_client.AppiumDriver;
import org.viesure.base.BasePage;


public class ArticleListPage extends BasePage {

    ArticleListPageLocators locators;

    public ArticleListPage(AppiumDriver<?> driver){
        super(driver);

        locators = new ArticleListPageLocators(driver);
    }

    public int getListSize(){
        return locators.articles.size();
    }

}

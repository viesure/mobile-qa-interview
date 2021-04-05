package org.viesure.articleListPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.viesure.articleDetailPage.ArticleDetailPage;

public class ArticleElement implements Comparable<ArticleElement>{
    String title;
    String author;
    String release_date;

    WebElement element;
    AndroidDriver<AndroidElement> driver;

    public ArticleElement(WebElement element, AndroidDriver<AndroidElement> driver){
        this.element = element;
        this.driver = driver;

        initProperties();
    }

    public void initProperties(){
        String elementText= element.getText();
        String[] split = elementText.split(",");
        title = split[0].trim();
        author = split[1].replace("author:","").trim();
        release_date = split[2].trim();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getRelease_date() {
        return release_date;
    }

    public WebElement getElement() {
        return element;
    }

    @Step("Clicking on article")
    public ArticleDetailPage clickOnArticle(){
        this.element.click();
        return new ArticleDetailPage(driver);
    }

    @Override
    public String toString(){
        return "Title:\t" + this.title +
                "\nAuthor:\t" + this.author +
                "\nRelease Date:\t" + this.release_date;
    }

    @Override
    public int compareTo(ArticleElement articleElement) {
        //comparing the getText as it returns a concatenated string unique to the element
        return this.element.getText().compareTo(articleElement.element.getText());
    }
}

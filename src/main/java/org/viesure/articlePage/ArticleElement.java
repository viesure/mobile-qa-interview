package org.viesure.articlePage;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class ArticleElement {
    String title;
    String author;
    String release_date;

    WebElement element;

    public ArticleElement(WebElement element){
        this.element = element;

        initProperties();
    }

    public void initProperties(){
        String elementText= element.getText();
        String[] split = elementText.split(",");
        title = split[0].trim();
        author = split[1].replace("author:","").trim();
        release_date = split[2].trim();
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

    @Step("Clicking on article: {title}")
    public void clickOnArticle(){
        this.element.click();
    }

    @Override
    public String toString(){
        return "Title:\t" + this.title +
                "\nAuthor:\t" + this.author +
                "\nRelease Date:\t" + this.release_date;
    }
}

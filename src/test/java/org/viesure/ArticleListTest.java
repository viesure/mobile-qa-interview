package org.viesure;


import org.testng.annotations.Test;
import org.viesure.articlePage.ArticleElement;
import org.viesure.articlePage.ArticleListPage;



public class ArticleListTest extends BaseTest {

    @Test
    public void openTest(){
        ArticleListPage page = new ArticleListPage(driver);


        for (ArticleElement article: page.getVisibleArticles()){
            System.out.println(article);
        }

    }
}
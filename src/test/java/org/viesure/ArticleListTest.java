package org.viesure;

import org.testng.annotations.Test;
import org.viesure.articlePage.ArticleListPage;



public class ArticleListTest extends BaseTest {

    @Test
    public void openTest(){

        ArticleListPage page = new ArticleListPage(driver);

        page.getListSize();
    }
}

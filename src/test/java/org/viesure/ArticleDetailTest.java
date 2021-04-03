package org.viesure;


import org.testng.annotations.Test;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.articlePage.ArticleElement;
import org.viesure.articlePage.ArticleListPage;


public class ArticleDetailTest extends BaseTest {

    @Test(description = "simple test that detail page opens and all information can be found")
    public void detailTest(){

        ArticleListPage page = new ArticleListPage(driver);

        page.getVisibleArticles().get(0).clickOnArticle();
        ArticleDetailPage detailPage = new ArticleDetailPage(driver);

        System.out.println(detailPage.getPageTitle());
        System.out.println(detailPage.getCreatedTime());
        System.out.println(detailPage.getTitle());
        System.out.println(detailPage.getDescription());
        System.out.println(detailPage.getAuthor());

        detailPage.clickShareButton();
        navigateBack();

    }
}

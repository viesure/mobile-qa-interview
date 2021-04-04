package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.viesure.articleListPage.ArticleElement;
import org.viesure.articleListPage.ArticleListPage;
import org.viesure.common.Article;
import org.viesure.utils.Networking;

import java.util.List;

public class ArticleListSteps {

    private AndroidDriver<AndroidElement> driver;
    ArticleListPage articleListPage;

    public ArticleListSteps(CommonSteps commonSteps){
        this.driver = commonSteps.getDriver();
    }

    @Given("user opens viesure application")
    @Step("user opens viesure application")
    public void user_opens_viesure_application() {
        articleListPage = new ArticleListPage(driver);

        articleListPage.getVisibleArticles();

        System.out.println("navigate to article list page");
    }

    @When("the application loads a list of articles")
    @Step("the application loads a list of articles")
    public void the_application_loads_a_list_of_articles() {

    }

    @Then("user can verify the visible articles")
    @Step("User can verify the visible articles")
    public void user_can_verify_the_visible_articles() {
        System.out.println("verify articles");
        List<Article> expectedArticles = Networking.getDummyData();
        List<ArticleElement> actualVisibleArticles = articleListPage.getVisibleArticles();

        for (int i=0; i< actualVisibleArticles.size(); i++){
            validateArticleData(actualVisibleArticles.get(i), expectedArticles.get(i));
        }

    }

    @Step("Testing data on article")
    private void validateArticleData(ArticleElement actualArticle, Article expectedArticle){

        Assert.assertEquals(actualArticle.getAuthor(), expectedArticle.getAuthor());
        Assert.assertEquals(actualArticle.getRelease_date(), expectedArticle.getRelease_date());
        Assert.assertEquals(actualArticle.getTitle(), expectedArticle.getTitle());
    }
}

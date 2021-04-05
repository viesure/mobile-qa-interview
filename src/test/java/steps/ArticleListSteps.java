package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.articleListPage.ArticleElement;
import org.viesure.articleListPage.ArticleListPage;
import org.viesure.common.Article;
import org.viesure.utils.AllureLogger;
import org.viesure.utils.Gestures;
import org.viesure.utils.Networking;

import java.util.ArrayList;
import java.util.List;

public class ArticleListSteps {

    private AndroidDriver<AndroidElement> driver;
    private GlobalHooks globalHooks;
    ArticleListPage articleListPage;

    ArticleDetailPage selectedDetailPage;

    public ArticleListSteps(GlobalHooks globalHooks){
        this.driver = globalHooks.getDriver();
        this.globalHooks = globalHooks;
    }

    @Given("user opens viesure application")
    @Step("user opens viesure application")
    public void user_opens_viesure_application() {
        articleListPage = new ArticleListPage(driver);

        articleListPage.getVisibleArticles();
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
        System.out.println("Asserting\n" + actualArticle + "\nVS\n" + expectedArticle);

        Assert.assertEquals(actualArticle.getAuthor(), expectedArticle.getAuthor());
        Assert.assertEquals(actualArticle.getRelease_date(), expectedArticle.getRelease_date());
        Assert.assertEquals(actualArticle.getTitle(), expectedArticle.getTitle());
    }

    @When("user scrolls to the bottom")
    @Step("user scrolls to the bottom")
    public void userScrollsToTheBottom() {
        articleListPage.scrollUntilBottomOfPage();
    }

    @When("user scrolls to the top")
    @Step("user scrolls to the top")
    public void userScrollsToTheTop() {
        articleListPage.scrollUntilTopOfPage();
    }

    @Then("user can see the articles list")
    @Step("user can see the articles list")
    public void userCanSeeTheArticlesList() {
        Assert.assertFalse(articleListPage.getVisibleArticles().isEmpty());
    }



    @When("user clicks on a visible article")
    @Step("user clicks on a visible article")
    public void userClicksOnAVisibleArticle() {
        selectedDetailPage = articleListPage.getVisibleArticles().get(0).clickOnArticle();
    }

    @Then("article detail page opens")
    @Step("article detail page opens")
    public void articleDetailPageOpens() {
        Assert.assertNotNull(selectedDetailPage);
    }

    @When("user clicks on the detail page back button")
    @Step("user clicks on the detail page back button")
    public void userClicksOnTheDetailPageBackButton() {
        articleListPage= selectedDetailPage.clickBackButton();
        selectedDetailPage=null;

    }

    @When("user clicks on the hardware back button")
    @Step("user clicks on the hardware back button")
    public void userClicksOnTheHardwareBackButton() {
        driver.navigate().back();
        articleListPage = new ArticleListPage(driver);
        selectedDetailPage=null;
    }

    @When("user clicks on article with {string}")
    @Step("user clicks on article with {string}")
    public void userClicksOnArticleWith(String title) {
        for (ArticleElement article: articleListPage.getVisibleArticles()){
            if (article.getTitle().equals(title)){
                selectedDetailPage = article.clickOnArticle();
            }
        }
    }

    @And("title of the page is the same as the clicked {string}")
    @Step("title of the page is the same as the clicked {string}")
    public void titleOfThePageIsTheSameAsTheClicked(String title) {
        Assert.assertEquals(selectedDetailPage.getTitle(), title);
        Assert.assertEquals(selectedDetailPage.getPageTitle(), title);
    }

    @Then("user can click on every article")
    public void userCanClickOnEveryArticle() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        while (clickedArticleTitles.size() != expectedList.size())
        {
            currentArticles= articleListPage.getVisibleArticles();

            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    AllureLogger.saveTextLog("Clicking on: " + article.getTitle());
                    ArticleDetailPage detailPage = article.clickOnArticle();
                    detailPage.clickBackButton();

                    clickedArticleTitles.add(article.getTitle());
                }
            }
            if (clickedArticleTitles.size() != expectedList.size()){
                Gestures.scrollDown(driver);
            }
        }

        System.out.println("Size at the end: " +clickedArticleTitles.size());
    }
}

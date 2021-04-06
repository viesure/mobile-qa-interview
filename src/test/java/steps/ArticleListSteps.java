package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
            verifyArticleListItem(actualVisibleArticles.get(i), expectedArticles.get(i));
        }

    }

//    @Step("Testing data on article")
//    private void validateArticleData(ArticleElement actualArticle, Article expectedArticle){
//        System.out.println("Asserting\n" + actualArticle + "\nVS\n" + expectedArticle);
//
//        Assert.assertEquals(actualArticle.getAuthor(), expectedArticle.getAuthor());
//        Assert.assertEquals(actualArticle.getRelease_date(), expectedArticle.getRelease_date());
//        Assert.assertEquals(actualArticle.getTitle(), expectedArticle.getTitle());
//    }

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
            currentArticles = articleListPage.getVisibleArticles();

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
    }

    @Then("every article and detail page information is correct")
    @Step("every article and detail page information is correct")
    public void everyArticleAndDetailPageInformationIsCorrect() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        //Articles are supposed to be in the same order as coming from backend
        //Index will be used to compare selected article with backend data
        int articleIndex = 0;

        while (clickedArticleTitles.size() != expectedList.size())
        {
            currentArticles = articleListPage.getVisibleArticles();

            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    AllureLogger.saveTextLog("Clicking on: " + article.getTitle());
                    verifyArticleListItem(article, expectedList.get(articleIndex));

                    ArticleDetailPage detailPage = article.clickOnArticle();
                    verifyArticleDetail(detailPage, expectedList.get(articleIndex));
                    detailPage.clickBackButton();

                    clickedArticleTitles.add(article.getTitle());
                    //Incrementing article index after adding it to clicked article lsit
                    articleIndex++;
                }
            }
            if (clickedArticleTitles.size() != expectedList.size()){
                Gestures.scrollDown(driver);
            }
        }
    }

    @Then("every shared article fills mail correctly")
    public void everySharedArticleFillsMailCorrectly() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        //Articles are supposed to be in the same order as coming from backend
        //Index will be used to compare selected article with backend data
        int articleIndex = 0;

        while (clickedArticleTitles.size() != expectedList.size())
        {
            currentArticles = articleListPage.getVisibleArticles();

            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    AllureLogger.saveTextLog("Clicking on: " + article.getTitle());

                    ArticleDetailPage detailPage = article.clickOnArticle();
                    detailPage.clickShareButton();

                    verifyMailData(expectedList.get(articleIndex));

                    detailPage.clickBackButton();

                    clickedArticleTitles.add(article.getTitle());
                    //Incrementing article index after adding it to clicked article lsit
                    articleIndex++;
                }
            }
            if (clickedArticleTitles.size() != expectedList.size()){
                Gestures.scrollDown(driver);
            }
        }
    }

    @Step("Verifying article list item content")
    private void verifyArticleListItem(ArticleElement actualArticle, Article expectedArticle){
        Assert.assertEquals(actualArticle.getTitle(), expectedArticle.getTitle(),"Testing if title is correct");
        Assert.assertEquals(actualArticle.getRelease_date(), expectedArticle.getRelease_date(),"Testing if release date is correct");
        Assert.assertEquals(actualArticle.getAuthorEmail(), expectedArticle.getAuthor(),"Testing if author is correct");
    }

    @Step("Verifying article detail content")
    private void verifyArticleDetail(ArticleDetailPage detailPage, Article expectedArticle){
        Assert.assertEquals(detailPage.getPageTitle(), expectedArticle.getTitle(),"Testing if page title is correct");
        Assert.assertEquals(detailPage.getTitle(), expectedArticle.getTitle(),"Testing if title is correct");
        Assert.assertEquals(detailPage.getCreatedTime(), expectedArticle.getRelease_date(),"Testing if release date is correct");
        Assert.assertEquals(detailPage.getDescription(), expectedArticle.getDescription(),"Testing if description is correct");
        Assert.assertEquals(detailPage.getAuthorEmail(), expectedArticle.getAuthor(),"Testing if author is correct");
    }

    @Step("Verifying filled email data")
    private void verifyMailData(Article expectedArticle){
        System.out.println("Testing app activity and gmail fill data");
        Assert.assertEquals(driver.currentActivity(), ".ComposeActivityGmailExternal", "Current Activity is not compose activity");

        WebDriverWait wait = new WebDriverWait(driver,10);
        WebElement recipient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/to")));
        WebElement subject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/subject")));
        WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/wc_body_layout")));

        //removing "author: " here as it can be needed on other places
        String expectedRecipientText = "<" + expectedArticle.getAuthor() + ">, ";
        Assert.assertEquals(recipient.getText(), expectedRecipientText, "Testing if actual recipient equals expected recipient");
        Assert.assertEquals(subject.getText(), expectedArticle.getTitle(), "Testing if subject equals to the title of the article");
        Assert.assertTrue(body.getText().isEmpty(), "Testing if email body is empty");

//        Set<String> contextNames = driver.getContextHandles();
//        String otherContext = (String) contextNames.toArray()[1];
//
//        driver.context((String) contextNames.toArray()[1]);
//
//        driver.closeApp();
//        driver.context("NATIVE_APP");
        //First one closes keyboard, second navigates back
        driver.navigate().back();
        driver.navigate().back();
    }
}

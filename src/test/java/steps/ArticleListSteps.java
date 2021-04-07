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
import org.viesure.gmailPage.GmailPage;
import org.viesure.utils.AllureLogger;
import org.viesure.utils.Gestures;
import org.viesure.utils.Networking;

import java.util.ArrayList;
import java.util.List;

public class ArticleListSteps {

    private AndroidDriver<AndroidElement> driver;

    ArticleListPage articleListPage;
    ArticleDetailPage selectedDetailPage;
    GmailPage gmailPage;

    public ArticleListSteps(GlobalHooks globalHooks){
        this.driver = globalHooks.getDriver();
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

    @When("user scrolls to the bottom")
    @Step("user scrolls to the bottom")
    public void userScrollsToTheBottom() {
        articleListPage.scrollToBottom();
    }

    @When("user scrolls to the top")
    @Step("user scrolls to the top")
    public void userScrollsToTheTop() {
        articleListPage.scrollToTop();
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
    @Step("user can click on every article")
    public void userCanClickOnEveryArticle() {
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;

        List<Exception> exceptions = new ArrayList<>();

        do {
            int currentArticleSize = clickedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    AllureLogger.saveTextLog("Clicking on: " + article.getTitle());
                    System.out.println("clicking on:" + article.getTitle());
                    try{
                        ArticleDetailPage detailPage = article.clickOnArticle();
                        detailPage.clickBackButton();
                    } catch (Exception ex){
                        AllureLogger.saveTextLog("Exception happened on article: " + article.getTitle());
                        AllureLogger.saveScreenshotPNG(driver);
                        exceptions.add(ex);
                    }
                    clickedArticleTitles.add(article.getTitle());
                }
            }
            Gestures.scrollDown(driver);

            if (clickedArticleTitles.size() == currentArticleSize){
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        verifyErrorListIsEmpty(exceptions);
    }

    @Then("every article and detail page information is correct")
    @Step("every article and detail page information is correct")
    public void everyArticleAndDetailPageInformationIsCorrect() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;
        int articleIndex = 0;

        List<Exception> exceptions = new ArrayList<>();

        do {
            int currentArticleSize = clickedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    AllureLogger.saveTextLog("Clicking on: " + article.getTitle());
                    verifyArticleListItem(article, expectedList.get(articleIndex));

                    try{
                        ArticleDetailPage detailPage = article.clickOnArticle();
                        verifyArticleDetail(detailPage, expectedList.get(articleIndex));
                        detailPage.clickBackButton();
                    } catch (Exception ex){
                        AllureLogger.saveTextLog("Exception happened on article: " + article.getTitle());
                        AllureLogger.saveScreenshotPNG(driver);
                        exceptions.add(ex);
                    }

                    clickedArticleTitles.add(article.getTitle());
                    //Incrementing article index after adding it to clicked article lsit
                    articleIndex++;
                }
            }
            Gestures.scrollDown(driver);

            //Check if the size of clicked articles have not increased increased
            if (clickedArticleTitles.size() == currentArticleSize){
                //Give exit condition
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        verifyErrorListIsEmpty(exceptions);
    }

    @Then("every shared article fills mail correctly")
    @Step("every shared article fills mail correctly")
    public void everySharedArticleFillsMailCorrectly() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;
        int articleIndex = 0;

        List<Exception> exceptions = new ArrayList<>();

        do {
            int currentArticleSize = clickedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    AllureLogger.saveTextLog("Clicking on: " + article.getTitle());
                    verifyArticleListItem(article, expectedList.get(articleIndex));

                    try{
                        AllureLogger.saveTextLog("Clicking on: " + article.getTitle());

                        ArticleDetailPage detailPage = article.clickOnArticle();
                        gmailPage = detailPage.clickShareButton();

                        verifyMailData(expectedList.get(articleIndex));

                        detailPage.clickBackButton();
                    } catch (Exception ex){
                        AllureLogger.saveTextLog("Exception happened on article: " + article.getTitle());
                        AllureLogger.saveScreenshotPNG(driver);
                        exceptions.add(ex);
                    }

                    clickedArticleTitles.add(article.getTitle());
                    //Incrementing article index after adding it to clicked article lsit
                    articleIndex++;
                }
            }
            Gestures.scrollDown(driver);

            //Check if the size of clicked articles have not increased increased
            if (clickedArticleTitles.size() == currentArticleSize){
                //Give exit condition
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        verifyErrorListIsEmpty(exceptions);

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
        Assert.assertEquals(gmailPage.getCurrentActivity(), ".ComposeActivityGmailExternal", "Current Activity is not compose activity");

        //removing "author: " here as it can be needed on other places
        String expectedRecipientText = "<" + expectedArticle.getAuthor() + ">, ";
        Assert.assertEquals(gmailPage.getRecipient(), expectedRecipientText, "Testing if actual recipient equals expected recipient");
        Assert.assertEquals(gmailPage.getSubject(), expectedArticle.getTitle(), "Testing if subject equals to the title of the article");
        Assert.assertTrue(gmailPage.getBody().isEmpty(), "Testing if email body is empty");

        driver.navigate().back();
        driver.navigate().back();
    }

    @Step("Verifying that no error has ben recorded during test")
    private void verifyErrorListIsEmpty(List<Exception> errorList){
        //In case error list is not empty, logging all recorded errors to allure
        for(Exception exception: errorList){
            AllureLogger.saveTextLog(exception.getLocalizedMessage());
        }
        //Using equals so we can see in the logs how many errors happened
        Assert.assertEquals(errorList.size(),0,"The list of errors should be empty");
    }

    @Then("every article from backend are displayed")
    @Step("every article from backend are displayed")
    public void everyArticleFromBackendAreDisplayed() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> observedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;
        int articleIndex = 0;

        do {
            int currentArticleSize = observedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!observedArticleTitles.contains(article.getTitle())){

                    verifyArticleListItem(article, expectedList.get(articleIndex));

                    observedArticleTitles.add(article.getTitle());
                    articleIndex++;
                }
            }
            Gestures.scrollDown(driver);

            if (observedArticleTitles.size() == currentArticleSize){
                //Give exit condition
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        //After observing every displayed article, asserting if there was the same number of articles observed
        //as it comes from the backend
        Assert.assertEquals(observedArticleTitles.size(), expectedList.size(), "the number of displayed articles are not the same as it comes from backend");
    }
}

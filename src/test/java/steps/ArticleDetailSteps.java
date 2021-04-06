package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.gmailPage.GmailPage;

public class ArticleDetailSteps {
    private AndroidDriver<AndroidElement> driver;

    ArticleDetailPage selectedDetailPage;
    GmailPage gmailPage;

    String authorEmail;
    String articleTitle;

    public ArticleDetailSteps(GlobalHooks globalHooks){
        this.driver = globalHooks.getDriver();

        selectedDetailPage = new ArticleDetailPage(driver);
    }


    @When("user clicks on the share button")
    @Step("user clicks on the share button")
    public void userClicksOnTheShareButton() {
        gmailPage = selectedDetailPage.clickShareButton();
    }

    @Then("the app should switch to gmail")
    @Step("the app should switch to gmail")
    public void theAppShouldSwitchToGmail() {
        String welcomeTourActivity = ".welcome.WelcomeTourActivity";
        String composeActivity = ".ComposeActivityGmailExternal";

        String currentActivity = gmailPage.getCurrentActivity();

        Assert.assertTrue( currentActivity.equals(welcomeTourActivity)|| currentActivity.equals(composeActivity));
    }

    @When("user checks the name of the author and the title")
    @Step("user checks the name of the author and the title")
    public void userChecksTheNameOfTheAuthorAndTheTitle() {
        this.authorEmail = selectedDetailPage.getAuthorEmail();
        this.articleTitle = selectedDetailPage.getTitle();
    }

    @And("the recipient and subject fields are filled")
    @Step("the recipient and subject fields are filled")
    public void theRecipientAndSubjectFieldsAreFilled() {

        Assert.assertEquals(gmailPage.getCurrentActivity(), ".ComposeActivityGmailExternal", "User is not in compose email activity.");

        //removing "author: " here as it can be needed on other places
        String expectedRecipientText = "<" + authorEmail.replace("author: ", "") + ">, ";
        Assert.assertEquals(gmailPage.getRecipient(), expectedRecipientText, "Testing if actual recipient equals expected recipient");
        Assert.assertEquals(gmailPage.getSubject(), articleTitle, "Testing if subject equals to the title of the article");
        Assert.assertTrue(gmailPage.getBody().isEmpty(), "Testing if email body is empty");
    }


    @And("user navigates back to viesure application")
    public void userNavigatesBackToViesureApplication() {
        //First one closes the keyboard, second navigates back
        driver.navigate().back();
        driver.navigate().back();
    }
}

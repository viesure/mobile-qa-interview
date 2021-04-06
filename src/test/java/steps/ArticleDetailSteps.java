package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.viesure.articleDetailPage.ArticleDetailPage;

public class ArticleDetailSteps {
    private AndroidDriver<AndroidElement> driver;

    ArticleDetailPage selectedDetailPage;
    String authorEmail;
    String articleTitle;

    public ArticleDetailSteps(GlobalHooks globalHooks){
        this.driver = globalHooks.getDriver();

        selectedDetailPage = new ArticleDetailPage(driver);
    }


    @When("user clicks on the share button")
    @Step("user clicks on the share button")
    public void userClicksOnTheShareButton() {
        selectedDetailPage.clickShareButton();
    }

    @Then("the app should switch to gmail")
    @Step("the app should switch to gmail")
    public void theAppShouldSwitchToGmail() {
        String welcomeTourActivity = ".welcome.WelcomeTourActivity";
        String composeActivity = ".ComposeActivityGmailExternal";
        String currentActivity = driver.currentActivity();

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
        Assert.assertEquals(driver.currentActivity(), ".ComposeActivityGmailExternal", "User is not in compose email activity.");

        WebDriverWait wait = new WebDriverWait(driver,10);
        WebElement recipient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/to")));
        WebElement subject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/subject")));
        WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/wc_body_layout")));

        //removing "author: " here as it can be needed on other places
        String expectedRecipientText = "<" + authorEmail.replace("author: ", "") + ">, ";
        Assert.assertEquals(recipient.getText(), expectedRecipientText, "Testing if actual recipient equals expected recipient");
        Assert.assertEquals(subject.getText(), articleTitle, "Testing if subject equals to the title of the article");
        Assert.assertTrue(body.getText().isEmpty(), "Testing if email body is empty");
    }


    @And("user navigates back to viesure application")
    public void userNavigatesBackToViesureApplication() {
        driver.navigate().back();
    }
}

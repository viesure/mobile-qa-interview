package org.viesure.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
        features = {"src/main/resources/pages"},
        glue = "steps",
        tags = "@gmailLoggedIn"
)
public class SlowTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void beforeRunnerTest(){
        System.out.println("Test runner before class");
    }
}

package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/java/resources/features",
		glue = "stepdefinitions",
		plugin = {"pretty","junit:target/junitreport.xml","json:target/jsonreport.json","html:target/cucumber-reports"},
		monochrome=true
		)

public class TestRunner extends AbstractTestNGCucumberTests{
	
}

package Runnerclass;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import org.junit.BeforeClass;
import org.testng.annotations.DataProvider;
import main.*;



@CucumberOptions(
		features = "src/test/java/features",
		glue = "stepdefinition", 
		plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		tags = "@EmpCreationandWageSubmission",
		monochrome = true,
		publish = true
	
    )
public class ParallelRunner extends AbstractTestNGCucumberTests {

//      @Override
//      @DataProvider(parallel = true)
//      public Object[][] scenarios() {
//            return super.scenarios();
//      }
     
}
package Runnerclass;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import main.*;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/java/Features/WageSubmission.feature",
		glue = "stepdefinition", 
		monochrome = true, 
		plugin = {
				"pretty", "html:target/cucumber", "json:target/Cucumber.json",
				"junit:target/Cucumber.xml" }, 
		tags = "@EmpCreationandWageSubmission"
	
    )

public class Runnerclass extends Mainclass{
	

	@BeforeClass
	public static void beforemethod() {
		initializeObjectProperty("Wage.yml");
	}
	
//	@After
//    public static void printscreenshot(){
//    String nameofCurrClass = new Throwable().getStackTrace()[0] .getClassName();
//    System.out.println("Name of current method is "+nameofCurrClass);
//	Takescreenshot(nameofCurrClass);
//    }
	
//	@AfterClass
//	public static void terminatebrowser() {
//		terminate();
//		
//	}
	
}

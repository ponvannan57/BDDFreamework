package Runnerclass;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import main.*;

@RunWith(Cucumber.class)
@CucumberOptions(
		features={"src/test/java/features/WageSubmission.feature"},
		glue="stepdefinition",
		plugin = {"pretty", "html:target/cucumber-reports"}
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

package stepdefinition;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.aspose.cells.ReplaceOptions;
import com.aspose.cells.Workbook;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import main.Mainclass;

public class Stepdefinition extends Mainclass{
	public static int Taxrate;
	public static String ActualUiroundOff;
	public static String ActualInterestRate;
	
	@Before
	public static void beforemethod() {
  		initializeObjectProperty("Wage.yml");
  	}
	
	@AfterStep
	public void addScreenshot(Scenario scenario) throws IOException {
		
		  File screenshot = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
		  byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
		  scenario.attach(fileContent, "image/png", "screenshot");
		
	}
	
//	@AfterStep
//	public void addScreenshot(Scenario scenario){
//
//		//validate if scenario has failed
//		if(scenario.isFailed()) {
//			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//			scenario.attach(screenshot, "image/png", "image"); 
//		}
//		
//	}
	
	@Given("^I Configure the Browser as \"([^\"]*)\" with URL \"([^\"]*)\"$")
    public void i_configure_the_browser_as_something_with_url_something(String Browser, String URL) throws Throwable {
		Browserselection(Browser,URL);	
    }
	
	@Then("^I Register an Employer As per Deatils from Below DataTable for Iteration (.+)$")
    public void i_register_an_employer_as_per_deatils_from_below_datatable_for_iteration(String iteration,DataTable Employerregistration) throws Throwable {
       
		 System.out.println("Iteration "+iteration);
			List <Map<String,String>>  EmpData = Employerregistration.asMaps(String.class,String.class);
		     String ServiceBeginDate = EmpData.get(0).get("ServiceBeginDate");
		     String ServiceEndDate = EmpData.get(0).get("ServiceEndDate");
		     String EMPAddress = EmpData.get(0).get("EMPAddress");
		     String EMPCity = EmpData.get(0).get("EMPCity");
		     String EMPState = EmpData.get(0).get("EMPState");
		     String EMPZipCode = EmpData.get(0).get("EMPZipCode");
		     String EMPEmail = EmpData.get(0).get("EMPEmail");
		     String EMPPhone = EmpData.get(0).get("EMPPhone");
		     String Yearwages = EmpData.get(0).get("Yearwages");
		     
		     Wait(10);
		     clickelement("xpath","HomePage.Employer");
		     clickelement("xpath","HomePage.EmployerRegistration");
		     Wait(10);
		     //Screen 1 - EMP Registration 
		     WaitForElement(20,"id","EMPRegNotificationScreen.radio_servicewage");
		     Selectradiobuttonbyname("xpath","EMPRegNotificationScreen.radio_servicewage","Yes");
		     SendText("id","EMPRegNotificationScreen.txt_noofind","4");	     
		     SendText("id","EMPRegNotificationScreen.txt_servicebegindate",ServiceBeginDate);	     
		     SendText("id","EMPRegNotificationScreen.txt_serviceenddate",ServiceBeginDate);
		     SendText("id","EMPRegNotificationScreen.txt_FEIN",	"496934827");
//		     WebElement FEIN = d.findElement(By.id(getElementProperty("EMPRegNotificationScreen.txt_FEIN")));
//		     	Dimension Size = FEIN.getSize();
//			    Point Diemension = FEIN.getLocation();
//			    System.out.println(Size);
//			    System.out.println(Diemension);
		     
		     clickelement("id","Common.btn_next");
		     
		     while(d.getPageSource().contains("Enter this employer's Federal Employer Identification Number (FEIN) is not a valid federal ein")||
		    		 d.getPageSource().contains("Enter this employer's Federal Employer Identification Number (FEIN) is not a valid #")) {
		    	 System.out.println("Inside Incorrect FEIN");
		    	 cleartext("id","EMPRegNotificationScreen.txt_FEIN");
		    	 SendText("id","EMPRegNotificationScreen.txt_FEIN",GenerateFEIN());
		    	 Wait(5);
		    	 clickelement("id","Common.btn_next");
		    	 
		     }
		     while(d.getPageSource().contains("The FEIN entered already exists in the system for another employer account")) {
		    	 clickelement("id","Common.btn_previous");
		    	 cleartext("id","EMPRegNotificationScreen.txt_FEIN");
		    	 SendText("id","EMPRegNotificationScreen.txt_FEIN",GenerateFEIN());
		    	 Wait(5);
		    	 clickelement("id","Common.btn_next");
		    	 
		     }
		     
		   //Screen 2 - EMP Registration
		     Selectradiobuttonbyname("id","EMPREmpLiabilitywages.radio_nonagri","Yes");
		     Selectradiobuttonbyname("id","EMPREmpLiabilitywages.radio_agri","No");
		     Selectradiobuttonbyname("id","EMPREmpLiabilitywages.radio_Domwages","No");
		     selectdropdownbytext("id", "EMPREmpLiabilitywages.ddl_wagesyear", Yearwages);
		     selectdropdownbytext("id", "EMPREmpLiabilitywages.ddl_wagequarter", "January, February, March (Q1)");
		     SendText("id","EMPREmpLiabilitywages.txt_grosswage",	"25220");
		     clickelement("id","Common.btn_next");
		     
		   //Screen 3 - EMP Registration
		     SelectradiobuttonbyValue("id", "OWNR");
		     clickelement("id","Common.btn_next");
		     
		   //Screen 4 - EMP Registration
		     SendText("id","EMPRAdminInfo.txt_firstname","Moeen");
		     SendText("id","EMPRAdminInfo.txt_lastname","Ali");
		     SendText("id","EMPRAdminInfo.txt_bussinessphoneno",EMPPhone);
		     SendText("id","EMPRAdminInfo.txt_admintitle","Mr");
		     SendText("id","EMPRAdminInfo.txt_email",EMPEmail);
		     SendText("id","EMPRAdminInfo.txt_reenteremail",EMPEmail);
		     selectcheckbox("id","chkAuthorize");
		     clickelement("id","Common.btn_next");
		     
		   //Screen 5 - EMP Registration
		     selectcheckbox("id","chkSameAsSrc");
		     clickelement("id","Common.btn_next");
		     
		     
		   //Screen 6 - Identification Info
		     
		     selectdropdownbytext("id", "EMPRIdentityInfo.ddl_entitytype", "Sole Proprietorship");
		     SendText("id","EMPRIdentityInfo.txt_legalname","Moeen");
		     SendText("id","EMPRIdentityInfo.txt_addressline1",EMPAddress);
		     SendText("id","EMPRIdentityInfo.txt_city",EMPCity);
		     SendText("id","EMPRIdentityInfo.txt_ZIPcode",EMPZipCode);
		     selectdropdownbytext("id", "EMPRIdentityInfo.ddl_state", EMPState);
		     SendText("id","EMPRIdentityInfo.txt_bussinessphone",EMPPhone);
		     SendText("id","EMPRIdentityInfo.txt_legalemail",EMPEmail);
		     SendText("id","EMPRIdentityInfo.txt_reenteremail",EMPEmail);
		     clickelement("id","Common.btn_next");
		     
		     //Screen 7 - Addressselection
		     
		     clickelement("xpath","Common.radio_address");
		     clickelement("id","Common.btn_confirm");
		     
		   //Screen 7 - Temporary User ID
		     
		     storeelementetext("id","EMPRTemID.txt_TEMPUserID");
		     String TEMPId = StoredSring;
		     System.out.println("***** The Temporary ID is --> "+TEMPId+" *****");
		     clickelement("id","Common.btn_next");
		     
		   //Screen 8 - Enter Emp Bussiness Info
		     
		     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_PEO","No");
		     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_ClinetPEO","No");
		     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_NMAC","No");
		     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_contarctors","No");
		     clickelement("id","Common.btn_next");
		     
		   //Screen 9 - Enter Emp Bussiness Info Cont
		     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_501","No");
		     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_REIM","No");
		     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_Agency","No");
		     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_purcase","No");
		     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_location","No");
		     clickelement("id","Common.btn_next");
		     
		     //Screen 10 - Enter Emp Incorp Info
		     
		     SendText("id","EMPRIncorInfo.txt_crs","87452123654");
		     SendText("id","EMPRIncorInfo.txt_incordate",ServiceBeginDate);
		     clickelement("id","Common.btn_next");
		     
		     //Screen 11 - Enter Physical address
		     selectdropdownbytext("id", "EMPREnterPhysicalAdd.checkbox_addresstype", "Mailing");
		     Selectradiobuttonbyname("id","EMPREnterPhysicalAdd.radio_phylocation","Yes");
		     Selectradiobuttonbyname("id","EMPREnterPhysicalAdd.radio_statewide","No");
		     clickelement("id","Common.btn_next");
		     
		     //Screen 12 - Business Record
		     selectdropdownbytext("id", "EMPRBussinessrecord.checkbox_bussinessrecord", "Mailing");
		     clickelement("id","Common.btn_next");   
		     
		     //Screen 13 - Additional Address
		     Selectradiobuttonbyname("id","EMPRAdditionalAddress.radio_additionaladdress","No");
		     clickelement("id","Common.btn_next");
		     
		     //Screen 14 - NAISC Description
		     SendText("id","EMPRNAICDescription.txt_NAICSDes","Test");
		     clickelement("id","Common.btn_next");
		   
		     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS1", 2);
		     clickelement("id","Common.btn_next"); 
		     
		     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS2", 1);
		     clickelement("id","Common.btn_next");
		     
		     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS3", 1);
		     clickelement("id","Common.btn_next");
		     
		     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS4", 1);
		     clickelement("id","Common.btn_next");
		     
		     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS5", 1);
		     clickelement("id","Common.btn_next");
		     clickelement("id","Common.btn_submit");
		     
		   
		     
		     //Screen 15 - Owner Officer Info
		     clickelement("id","Common.btn_indowner");
		     SendText("id","EMPROwnerofficerinfo.txt_ownerfirstname","Moeen");
		     SendText("id","EMPROwnerofficerinfo.txt_ownerlatname","Ali");
		     SendText("id","EMPROwnerofficerinfo.txt_percentage","100");
		     SendText("id","EMPROwnerofficerinfo.txt_firstownershipdate",ServiceBeginDate);
		     SendText("id","EMPROwnerofficerinfo.txt_addressline1",EMPAddress);
		     SendText("id","EMPROwnerofficerinfo.txt_ownercity",EMPCity);
		     selectdropdownbytext("id", "EMPROwnerofficerinfo.ddl_ownerstate", EMPState);
		     SendText("id","EMPROwnerofficerinfo.txt_ownerzip",EMPZipCode);
		     SendText("id","EMPROwnerofficerinfo.txt_ownerphone",EMPPhone);
		     SendText("id","EMPROwnerofficerinfo.txt_owneremail",EMPEmail);
		     SendText("id","EMPROwnerofficerinfo.txt_reenterowneremail",EMPEmail);
		     clickelement("id","Common.btn_save");
		     clickelement("xpath","Common.radio_address");
		     clickelement("id","Common.btn_confirm");
		     clickelement("id","Common.btn_next");
		     ScrollToElement("xpath","Common.btn_submit2");
		     clickelement("xpath","Common.btn_submit2");
		     //Storing EAN and Username
		     storeelementetext("id","Common.txt_EAN");
		     String EAN = StoredSring;
		     System.out.println("***** The EAN is --> "+EAN+" *****");
		     storeelementetext("id","Common.txt_Username");
		     String Username = StoredSring;
		     System.out.println("***** The Username is --> "+Username+" *****");
//		     clickelement("id","Common.btn_Home");  
		     terminate();
		
    }
	
	@Then("^I Register an Employer As per Deatils from Below DataTable$")
    public void i_register_an_employer_as_per_deatils_from_below_datatable(DataTable Employerregistration) throws Throwable {
	    System.out.println("Inside setp definiit");
		List <Map<String,String>>  EmpData = Employerregistration.asMaps(String.class,String.class);
	     String ServiceBeginDate = EmpData.get(0).get("ServiceBeginDate");
	     String ServiceEndDate = EmpData.get(0).get("ServiceEndDate");
	     String EMPAddress = EmpData.get(0).get("EMPAddress");
	     String EMPCity = EmpData.get(0).get("EMPCity");
	     String EMPState = EmpData.get(0).get("EMPState");
	     String EMPZipCode = EmpData.get(0).get("EMPZipCode");
	     String EMPEmail = EmpData.get(0).get("EMPEmail");
	     String EMPPhone = EmpData.get(0).get("EMPPhone");
	     String Yearwages = EmpData.get(0).get("Yearwages");
	     Wait(10);
	     clickelement("xpath","HomePage.Employer");
	     clickelement("xpath","HomePage.EmployerRegistration");
	     Wait(10);
	     //Screen 1 - EMP Registration 
	     WaitForElement(20,"id","EMPRegNotificationScreen.radio_servicewage");
	     Selectradiobuttonbyname("xpath","EMPRegNotificationScreen.radio_servicewage","Yes");
	     SendText("id","EMPRegNotificationScreen.txt_noofind","4");	     
	     SendText("id","EMPRegNotificationScreen.txt_servicebegindate",ServiceBeginDate);	     
	     SendText("id","EMPRegNotificationScreen.txt_serviceenddate",ServiceBeginDate);
	     SendText("id","EMPRegNotificationScreen.txt_FEIN",	"496934827");
//	     WebElement FEIN = d.findElement(By.id(getElementProperty("EMPRegNotificationScreen.txt_FEIN")));
//	     	Dimension Size = FEIN.getSize();
//		    Point Diemension = FEIN.getLocation();
//		    System.out.println(Size);
//		    System.out.println(Diemension);
	     
	     clickelement("id","Common.btn_next");
	     
	     while(d.getPageSource().contains("Enter this employer's Federal Employer Identification Number (FEIN) is not a valid federal ein")||
	    		 d.getPageSource().contains("Enter this employer's Federal Employer Identification Number (FEIN) is not a valid #")) {
	    	 System.out.println("Inside Incorrect FEIN");
	    	 cleartext("id","EMPRegNotificationScreen.txt_FEIN");
	    	 SendText("id","EMPRegNotificationScreen.txt_FEIN",GenerateFEIN());
	    	 Wait(5);
	    	 clickelement("id","Common.btn_next");
	    	 
	     }
	     while(d.getPageSource().contains("The FEIN entered already exists in the system for another employer account")) {
	    	 clickelement("id","Common.btn_previous");
	    	 cleartext("id","EMPRegNotificationScreen.txt_FEIN");
	    	 SendText("id","EMPRegNotificationScreen.txt_FEIN",GenerateFEIN());
	    	 Wait(5);
	    	 clickelement("id","Common.btn_next");
	    	 
	     }
	     
	   //Screen 2 - EMP Registration
	     Selectradiobuttonbyname("id","EMPREmpLiabilitywages.radio_nonagri","Yes");
	     Selectradiobuttonbyname("id","EMPREmpLiabilitywages.radio_agri","No");
	     Selectradiobuttonbyname("id","EMPREmpLiabilitywages.radio_Domwages","No");
	     selectdropdownbytext("id", "EMPREmpLiabilitywages.ddl_wagesyear", Yearwages);
	     selectdropdownbytext("id", "EMPREmpLiabilitywages.ddl_wagequarter", "January, February, March (Q1)");
	     SendText("id","EMPREmpLiabilitywages.txt_grosswage",	"25220");
	     clickelement("id","Common.btn_next");
	     
	   //Screen 3 - EMP Registration
	     SelectradiobuttonbyValue("id", "OWNR");
	     clickelement("id","Common.btn_next");
	     
	   //Screen 4 - EMP Registration
	     SendText("id","EMPRAdminInfo.txt_firstname","Moeen");
	     SendText("id","EMPRAdminInfo.txt_lastname","Ali");
	     SendText("id","EMPRAdminInfo.txt_bussinessphoneno",EMPPhone);
	     SendText("id","EMPRAdminInfo.txt_admintitle","Mr");
	     SendText("id","EMPRAdminInfo.txt_email",EMPEmail);
	     SendText("id","EMPRAdminInfo.txt_reenteremail",EMPEmail);
	     selectcheckbox("id","chkAuthorize");
	     clickelement("id","Common.btn_next");
	     
	   //Screen 5 - EMP Registration
	     selectcheckbox("id","chkSameAsSrc");
	     clickelement("id","Common.btn_next");
	     
	     
	   //Screen 6 - Identification Info
	     
	     selectdropdownbytext("id", "EMPRIdentityInfo.ddl_entitytype", "Sole Proprietorship");
	     SendText("id","EMPRIdentityInfo.txt_legalname","Moeen");
	     SendText("id","EMPRIdentityInfo.txt_addressline1",EMPAddress);
	     SendText("id","EMPRIdentityInfo.txt_city",EMPCity);
	     SendText("id","EMPRIdentityInfo.txt_ZIPcode",EMPZipCode);
	     selectdropdownbytext("id", "EMPRIdentityInfo.ddl_state", EMPState);
	     SendText("id","EMPRIdentityInfo.txt_bussinessphone",EMPPhone);
	     SendText("id","EMPRIdentityInfo.txt_legalemail",EMPEmail);
	     SendText("id","EMPRIdentityInfo.txt_reenteremail",EMPEmail);
	     clickelement("id","Common.btn_next");
	     
	     //Screen 7 - Addressselection
	     
	     clickelement("xpath","Common.radio_address");
	     clickelement("id","Common.btn_confirm");
	     
	   //Screen 7 - Temporary User ID
	     
	     storeelementetext("id","EMPRTemID.txt_TEMPUserID");
	     String TEMPId = StoredSring;
	     System.out.println("***** The Temporary ID is --> "+TEMPId+" *****");
	     clickelement("id","Common.btn_next");
	     
	   //Screen 8 - Enter Emp Bussiness Info
	     
	     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_PEO","No");
	     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_ClinetPEO","No");
	     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_NMAC","No");
	     Selectradiobuttonbyname("id","EMPRBussinessINFO.radio_contarctors","No");
	     clickelement("id","Common.btn_next");
	     
	   //Screen 9 - Enter Emp Bussiness Info Cont
	     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_501","No");
	     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_REIM","No");
	     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_Agency","No");
	     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_purcase","No");
	     Selectradiobuttonbyname("id","EMPRBussinessINFOCont.radio_location","No");
	     clickelement("id","Common.btn_next");
	     
	     //Screen 10 - Enter Emp Incorp Info
	     
	     SendText("id","EMPRIncorInfo.txt_crs","87452123654");
	     SendText("id","EMPRIncorInfo.txt_incordate",ServiceBeginDate);
	     clickelement("id","Common.btn_next");
	     
	     //Screen 11 - Enter Physical address
	     selectdropdownbytext("id", "EMPREnterPhysicalAdd.checkbox_addresstype", "Mailing");
	     Selectradiobuttonbyname("id","EMPREnterPhysicalAdd.radio_phylocation","Yes");
	     Selectradiobuttonbyname("id","EMPREnterPhysicalAdd.radio_statewide","No");
	     clickelement("id","Common.btn_next");
	     
	     //Screen 12 - Business Record
	     selectdropdownbytext("id", "EMPRBussinessrecord.checkbox_bussinessrecord", "Mailing");
	     clickelement("id","Common.btn_next");   
	     
	     //Screen 13 - Additional Address
	     Selectradiobuttonbyname("id","EMPRAdditionalAddress.radio_additionaladdress","No");
	     clickelement("id","Common.btn_next");
	     
	     //Screen 14 - NAISC Description
	     SendText("id","EMPRNAICDescription.txt_NAICSDes","Test");
	     clickelement("id","Common.btn_next");
	   
	     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS1", 2);
	     clickelement("id","Common.btn_next"); 
	     
	     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS2", 1);
	     clickelement("id","Common.btn_next");
	     
	     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS3", 1);
	     clickelement("id","Common.btn_next");
	     
	     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS4", 1);
	     clickelement("id","Common.btn_next");
	     
	     selectdropdownbyindex("id", "EMPRNAICDescription.checkbox_NAICS5", 1);
	     clickelement("id","Common.btn_next");
	     clickelement("id","Common.btn_submit");
	     
	   
	     
	     //Screen 15 - Owner Officer Info
	     clickelement("id","Common.btn_indowner");
	     SendText("id","EMPROwnerofficerinfo.txt_ownerfirstname","Moeen");
	     SendText("id","EMPROwnerofficerinfo.txt_ownerlatname","Ali");
	     SendText("id","EMPROwnerofficerinfo.txt_percentage","100");
	     SendText("id","EMPROwnerofficerinfo.txt_firstownershipdate",ServiceBeginDate);
	     SendText("id","EMPROwnerofficerinfo.txt_addressline1",EMPAddress);
	     SendText("id","EMPROwnerofficerinfo.txt_ownercity",EMPCity);
	     selectdropdownbytext("id", "EMPROwnerofficerinfo.ddl_ownerstate", EMPState);
	     SendText("id","EMPROwnerofficerinfo.txt_ownerzip",EMPZipCode);
	     SendText("id","EMPROwnerofficerinfo.txt_ownerphone",EMPPhone);
	     SendText("id","EMPROwnerofficerinfo.txt_owneremail",EMPEmail);
	     SendText("id","EMPROwnerofficerinfo.txt_reenterowneremail",EMPEmail);
	     clickelement("id","Common.btn_save");
	     
	     clickelement("xpath","Common.radio_address");
	     clickelement("id","Common.btn_confirm");
	     clickelement("id","Common.btn_next");
	     ScrollToElement("xpath","Common.btn_submit2");
	     clickelement("xpath","Common.btn_submit2");
	     
	     //Storing EAN and Username
	     storeelementetext("id","Common.txt_EAN");
	      EAN = StoredSring;
	     System.out.println("***** The EAN is --> "+EAN+" *****");
	     storeelementetext("id","Common.txt_Username");
	      Username = StoredSring;
	     System.out.println("***** The Username is --> "+Username+" *****");
//	     clickelement("id","Common.btn_Home");       
	}
	
	

    @Then("^Login with the username (.+)$")
    public void login_with_the_username(String username) throws Throwable {
    	SendText("xpath","HomePage.Username",username);
    	clickelement("xpath","HomePage.Loginbutton"); 
        
    }
    
    @And("^I navigate to Wage Submission$")
    public void i_navigate_to_wage_submission() throws Throwable {
    	clickelement("xpath","WageSubmission.btn_wgedetails"); 
    	clickelement("xpath","WageSubmission.btn_wagesubmission"); 
    }

    
    @And("^I Login With Newly Created Employer and navigate to wage submission$")
    public void i_login_with_newly_created_employer_and_navigate_to_wage_submission() throws Throwable {
    	clickelement("id","Common.btn_Home"); 
    	clickelement("xpath","WageSubmission.btn_wgedetails"); 
    	clickelement("xpath","WageSubmission.btn_wagesubmission"); 
        
    }

    @When("^I select year (.+) and quarter (.+) for wage submission$")
    public void i_select_year_and_quarter_for_wage_submission(String year, String quarter) throws Throwable {
    	selectdropdownbytext("id", "WageSubmission.ddl_wageyear", year);
    	selectdropdownbytext("id", "WageSubmission.ddl_wagequarter", quarter);
        
    }
    
    @Then("^I select wage submission method as (.+)$")
    public void i_select_wage_submission_method_as(String method) throws Throwable {
    	
    	switch(method){
    	
    	case"File":
    		Selectradiobuttonbyindex("xpath","WageSubmission.radio_filigmethod","1");
    		break;
    	case"Copy":
    		Selectradiobuttonbyindex("xpath","WageSubmission.radio_filigmethod","2");
    		break;
    	case"Manual":
    		Selectradiobuttonbyindex("xpath","WageSubmission.radio_filigmethod","3");
    		break;
    	case"No":
    		Selectradiobuttonbyindex("xpath","WageSubmission.radio_filigmethod","4");
    		break;
    	
    	}
    	clickelement("id","Common.btn_next");
       
    }
    
    @And("^I select the File type as (.+)$")
    public void i_select_the_file_type_as(String filetype) throws Throwable {
    	SelectElementbyValue("id",filetype);
    	Selectradiobuttonbyname("id","WageSubmission.radio_filetype","Yes");
    	clickelement("id","Common.btn_next");
    
    }
    
    		
    @Then("^I replace the data for File Type as (.+) File Name (.+) for Year (.+) and Quarter (.+)$")
    public void i_replace_the_data_for_file_type_as_file_name_for_year_and_quarter(String filetype, String filename, String year, String quarter) throws Throwable {
    	findandreplacedatainfile(filetype,filename,year,quarter);
    }

 
    
    @Then("^I Upload the file (.+) and (.+)$")
    public void i_upload_the_file_and(String filename, String filetype) throws Throwable {
    	Fileupload(filename,filetype);
    	clickelement("id","Common.btn_next");
    }

    
    @And("^I selct Overwrite or merge option (.+) if it gets displayed$")
    public void i_selct_overwrite_or_merge_option_if_it_gets_displayed(String option) throws Throwable {
    	Wait(5);
    	
    	try {
    		switch(option){
    		case"Merge":
    			clickelement("xpath","WageSubmission.radio_merge");
    			clickelement("id","Common.btn_next");
    			break;
    		case"Overwrite":
    			clickelement("xpath","WageSubmission.radio_overwrite");
    			clickelement("id","Common.btn_next");
    			break;
    		
    	}
    }
    	catch(Exception e) {
    		
    		e.printStackTrace();
    		
    	}
//    	clickelement("xpath","WageSubmission.radio_merge");
//		clickelement("id","Common.btn_next");	
    }
    
    
    @And("^I Select ignore errors and proceed to next screen$")
    public void i_select_ignore_errors_and_proceed_to_next_screen() throws Throwable {
    	try {
    		Selectradiobuttonbyindex("id", "WageSubmission.radio_selectaction", "1");
    }
    	catch(Exception e) {
    		e.printStackTrace();	
    	}
    	clickelement("id","Common.btn_next");
       
    }
    
    @When("^I click on button (.+) and proceed$")
    public void i_click_on_button_and_proceed(String button) throws Throwable {
    	switch(button){
    	
    	case"Next":
    		clickelement("id","Common.btn_next");
    		break;
    	case"Previous":
    		clickelement("id","Common.btn_previous");
    		break;
    	case"SaveandExit":
    		clickelement("id","Common.btn_saveabdexit");
    		break;
    	
    	}
    }
    

    
    @Then("^I calculate the Interest for Month (.+) and Quarter (.+)$")
    public void i_calculate_the_interest_for_month_and_quarter(int wagesubmissionyear, String quarter) throws Throwable {
    	interestmonthcalculator(wagesubmissionyear, quarter);
    	System.out.println(wagetotalmonths);
    	getTableData("WageSubmission.table_UITaxTable",4,2);
    	System.out.println(Tabledata);
    	String UITaxableage = Tabledata.replace(" ", "").replace("$", "").replace(",", "");
    	double UITaxableage1 = Double.parseDouble(UITaxableage);
    	getTableData("WageSubmission.table_UITaxTable",6,2);
    	System.out.println(Tabledata);
    	String UIContribution = Tabledata.replace(" ", "").replace("$", "").replace(",", "");
//    	double UIContribution1 = Double.parseDouble(UIContribution);
    	storeelementetext("id","WageSubmission.txt_UIContribution");
    	String UIrate = StoredSring.replace(" ", "").replace("%", "").replace(",", "");
    	double UIrate1 = Double.parseDouble(UIrate);
    	double ActualUiContribution = UITaxableage1*(UIrate1/100);
    	double ActualUiContributionroundOff = Math.round(ActualUiContribution * 100.0) / 100.0;
    	if((ActualUiContributionroundOff*100)%10==0){
    		
    		ActualUiroundOff = String.format("%.2f", ActualUiContributionroundOff);
    		System.out.println(ActualUiroundOff);
    		
    	}
    	
    	else {
    		ActualUiroundOff = String.valueOf(ActualUiContributionroundOff);
    		System.out.println(ActualUiroundOff);
    	}
    	
    	assertEquals(ActualUiroundOff, UIContribution);	
    	System.out.println("The Actual UIContribution --> "+ActualUiroundOff+" is same as UIContribution -->"+UIContribution);
    	
    	getTableData("WageSubmission.table_UITaxTable",12,2);
    	System.out.println(Tabledata);
    	String Interest = Tabledata.replace(" ", "").replace("$", "");
    	double Wagemonths = wagetotalmonths;
    	double ActualUiround = Double.parseDouble(ActualUiroundOff);
    	System.out.println(ActualUiround);
//    	double DoubleUI = Double.parseDouble(Interest);
    	double ActualInterest = Wagemonths*(ActualUiround*0.01);
    	System.out.println(ActualInterest);
    	
		if((ActualInterest*100)%10==0){
		    		
				ActualInterestRate = String.format("%.2f", ActualInterest);
				System.out.println(ActualInterestRate);
				
			}
			
			else {
				ActualInterestRate = String.valueOf(ActualInterest);
			}
		assertEquals(ActualInterestRate, Interest);	
		System.out.println("The Actual interest --> "+ActualInterestRate+" is same as interest -->"+Interest);
    }
    
    
    @After
	public static void terminatebrowser() {
		terminate();
		
	}
    
    }


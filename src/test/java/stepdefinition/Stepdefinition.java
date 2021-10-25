package stepdefinition;

import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import main.Mainclass;

public class Stepdefinition extends Mainclass{
	
	@Given("^I Configure the Browser as \"([^\"]*)\" with URL \"([^\"]*)\"$")
    public void i_configure_the_browser_as_something_with_url_something(String Browser, String URL) throws Throwable {
		Browserselection(Browser,URL);	
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
	     
	     while(d.getPageSource().contains("Enter this employer's Federal Employer Identification Number (FEIN) is not a valid federal ein")) {
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
	     
	     
	       
	}
	
    }


package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;

public class Mainclass {
	
	public static WebDriver d;
	public Actions Act;
	public static Map<?,?> property;
	public static String Parentfolder = "objectrepository/";
	public static String Filepath;
	public String nameofCurrClass = new Throwable().getStackTrace()[0] .getClassName();
	public static JavascriptExecutor JS = (JavascriptExecutor)d;
	public static Select Select;
	public static WebElement WE;
	public static String TemporaryID;
	public static String Username;
	public static String EAN;
	public static String StoredSring;
	public static int Gracemonth;
	public static int totalmonths;
	public static int wagetotalmonths;
	public static String Tabledata;

	
    
	@SuppressWarnings("deprecation")

	public  void Browserselection (String Browser, String URL) {

		
		switch(Browser) {
		
		case"Chrome":
			
			System.setProperty("webdriver.chrome.driver", "src\\test\\Browsers\\chromedriver.exe");
		    System.out.println("The Browser Selected is Chrome");
		    ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			d= new ChromeDriver(capabilities);
			break;
		case"IE":
			System.setProperty("webdriver.ie.driver", "src\\test\\Browsers\\IEDriverServer.exe");
			d= new InternetExplorerDriver();
			break;
		case"Firefox":
			System.setProperty("webdriver.gecko.driver", "src\\test\\Browsers\\geckodriver.exe");
			d= new FirefoxDriver();
			break;
		
		}
		d.manage().window().maximize();
		String URLYML = "URL.".concat(URL);
		d.get(getElementProperty(URLYML));
		Takescreenshot(nameofCurrClass);
		
	}

	public static void initializeObjectProperty(String Filename)
	 {
		Filepath = Parentfolder.concat(Filename);
		System.out.println(Filepath);
	  try {
	   Reader rd = new FileReader(Filepath);
	   Yaml yaml = new Yaml();
	   property = (Map<?,?>) yaml.load(rd);
	   System.out.println(property);
	   rd.close();
	  } catch (FileNotFoundException e) {
	   
	   e.printStackTrace();
	  } catch (IOException e) {
	  
	   e.printStackTrace();
	  }
	 }
	
	public static String getElementProperty(String objectstring)

	 {  
	Map <?,?> map = (Map<?,?>) property.get(objectstring.split("\\.")[0]); 
	  return map.get(objectstring.split("\\.")[1]).toString();

	 }

	public static void Takescreenshot(String nameofCurrClass) {
		
		Date date = new Date();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy");  
		LocalDateTime now = LocalDateTime.now();
		String Mainfolder = "Run_"+dtf.format(now);
		String Name = date.toString().replace(":", "_").replace(" ", "_")+".jpg";
		String Filename = "\\C\\reports\\Screenshots\\"+Mainfolder+"\\"+nameofCurrClass+"\\"+Name;
		
		try {
		
		File Src = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(Src, new File(Filename));
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}
		
	}

	public static String fileread(String filename, String sheetname,int cellindex, int rowindex) throws IOException {
		
		File path = new File(filename);
		FileInputStream FIS = new FileInputStream(path);
		XSSFWorkbook Wb = new XSSFWorkbook(FIS);
		XSSFSheet Sheet = Wb.getSheet(sheetname);
		String DATA;
		
		try {
			
			DATA = Sheet.getRow(rowindex).getCell(rowindex).getStringCellValue();
			
		}
		catch(NullPointerException e){
			DATA=" ";
			
		}
		Wb.close();
		return DATA;
		
	}

	public static void filewrite(String filename, String sheetname,int cellindex, int rowindex, String DATA) throws IOException {
		File path = new File(filename);
		FileInputStream FIS = new FileInputStream(path);
		XSSFWorkbook WB = new XSSFWorkbook(FIS);
		XSSFSheet Sheet = WB.getSheet(sheetname);
		
		if(Sheet.getRow(rowindex)==null) {
			
			Sheet.createRow(rowindex).createCell(rowindex).setCellValue(DATA);
			
		}
		
		else {
			Sheet.getRow(rowindex).getCell(rowindex).setCellValue(DATA);
		}
		
		FileOutputStream FOS = new FileOutputStream(path);
		WB.write(FOS);
		WB.close();
		
	}

	public static void Selectradiobuttonbyname(String locator,String Elementpath,String Name) {
		
		
		switch(locator) {
		
		case"xpath":
			d.findElement(By.xpath("//div[@id='"+getElementProperty(""+Elementpath+"")+"']//child::label[contains(text(),'"+Name+"')]")).click();
			break;
		case"id":
			d.findElement(By.xpath("//div[@id='"+getElementProperty(""+Elementpath+"")+"']//child::label[contains(text(),'"+Name+"')]")).click();
			break;
		
		}
		
	}
	
public static void SelectElementbyValue(String locator,String Value) {
	
		
		switch(locator) {
		
		case"xpath":
			d.findElement(By.xpath("//input[@type='radio' and @value='"+Value+"']")).click();
			break;
		case"id":
			d.findElement(By.xpath("//input[@type='radio' and @value='"+Value+"']")).click();
			break;
		
		}
		
	}

	public static void Selectradiobuttonbyindex(String locator,String Elementpath,String Index) {
		
		
		switch(locator) {
		
		case"xpath":
			d.findElement(By.xpath("(//div[@id='"+getElementProperty(""+Elementpath+"")+"']//child::label)["+Index+"]")).click();
			break;
		case"id":
			d.findElement(By.xpath("(//div[@id='"+getElementProperty(""+Elementpath+"")+"']//child::label)["+Index+"]")).click();
			break;
		
		}
	
	}

	public static String GenerateFEIN() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999999);
	    System.out.println(String.format("%09d", number));
	    // this will convert any number sequence into 6 character.
	    return String.format("%09d", number);
	  
	}

	public static void clickelement(String locator, String Elementpath) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			d.findElement(By.xpath(getElementProperty(""+Elementpath+""))).click();
			break;
		case"id":
			d.findElement(By.id(getElementProperty(""+Elementpath+""))).click();
			break;
		case"cssSelector":
			d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))).click();
			break;
		
		}
}

	public static void javascriptclickelement(String locator, String Elementpath) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			WebElement element = d.findElement(By.xpath(getElementProperty(""+Elementpath+"")));
			JS.executeScript("arguments[0].click();", element);
			break;
		case"id":
			WebElement element1 =d.findElement(By.id(getElementProperty(""+Elementpath+"")));
			JS.executeScript("arguments[0].click();", element1);
			break;
		case"cssSelector":
			WebElement element2 = d.findElement(By.cssSelector(getElementProperty(""+Elementpath+"")));
			JS.executeScript("arguments[0].click();", element2);
			break;
		
		}
	
	}

	public static void cleartext(String locator, String Elementpath) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			d.findElement(By.xpath(getElementProperty(""+Elementpath+""))).clear();
			break;
		case"id":
			d.findElement(By.id(getElementProperty(""+Elementpath+""))).clear();
			break;
		case"cssSelector":
			d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))).clear();
			break;
		
		}
	
	}

	public static void SendText(String locator, String Elementpath, String Text) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			d.findElement(By.xpath(getElementProperty(""+Elementpath+""))).sendKeys(Text);
			break;
		case"id":
			d.findElement(By.id(getElementProperty(""+Elementpath+""))).sendKeys(Text);
			break;
		case"cssSelector":
			d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))).sendKeys(Text);
			break;
		
		}

	}
	
	public static void javaSriptSendText(String locator, String Elementpath, String Text) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			WebElement element = d.findElement(By.xpath(getElementProperty(""+Elementpath+"")));
			JS.executeScript("arguments[0].value='"+Text+"'", element);
			break;
		case"id":
			WebElement element1 =d.findElement(By.id(getElementProperty(""+Elementpath+"")));
			JS.executeScript("arguments[0].value='"+Text+"'", element1);
			break;
		case"cssSelector":
			WebElement element2 = d.findElement(By.cssSelector(getElementProperty(""+Elementpath+"")));
			JS.executeScript("arguments[0].value='"+Text+"'", element2);
			break;
		
		}

	}

	public static void Wait(long Time) {
		
		d.manage().timeouts().implicitlyWait(Time,TimeUnit.SECONDS) ;
		
	}
	
	public static void  WaitForElement(long Time, String locator,String Elementpath) {
		
		WebDriverWait wait = new WebDriverWait(d,Time);
		
		
		switch(locator) {
		
		case"xpath":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getElementProperty(""+Elementpath+""))));
			break;
		case"id":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getElementProperty(""+Elementpath+""))));
			break;
		case"cssSelector":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(getElementProperty(""+Elementpath+""))));
			break;
}
		
		
		
	}

	public static void ScrollToElement (String locator,String Elementpath) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			WebElement Element1 =d.findElement(By.xpath(getElementProperty(""+Elementpath+"")));
			((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView(true);", Element1);
			break;
		case"id":
			WebElement Element2 =d.findElement(By.id(getElementProperty(""+Elementpath+"")));
			((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView(true);", Element2);
			break;
		case"cssSelector":
			WebElement Element3 =d.findElement(By.cssSelector(getElementProperty(""+Elementpath+"")));
			((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView(true);", Element3);
			break;
			

		
		}
			
		}

	public static void switchbetweenwindows() {
    	
    	String Parentwindow = d.getWindowHandle();
    	Set<String> s = d.getWindowHandles();
    	Iterator<String> I = s.iterator();
    	
    	while(I.hasNext()) {
    		
    		String ChildWindow = I.next();
    		
    		if(!Parentwindow.equals(ChildWindow)) {
    			d.switchTo().window(ChildWindow);
    			System.out.println("The Title of the window is "+d.getTitle());
    			
    		}
    		
    	}
    	
    	d.switchTo().window(Parentwindow);
		System.out.println("The Title of the parent Window is "+d.getTitle());
    	
    	
    }
		
		public static void selectdropdownbyvalue(String locator, String Elementpath, String Value) {
			
			WaitForElement(10,locator,Elementpath);
			
		switch(locator) {
		
		case"xpath":
			Select = new Select( d.findElement(By.xpath(getElementProperty(""+Elementpath+""))));
			Select.selectByValue(Value);
			break;
		case"id":
			Select = new Select (d.findElement(By.id(getElementProperty(""+Elementpath+""))));
			Select.selectByValue(Value);
			break;
		case"cssSelector":
			Select = new Select (d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))));
			Select.selectByValue(Value);
			break;
		
		}
		
	}
	
	public static void selectdropdownbyindex(String locator, String Elementpath, int Index) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			Select = new Select( d.findElement(By.xpath(getElementProperty(""+Elementpath+""))));
			Select.selectByIndex(Index);
			break;
		case"id":
			Select = new Select (d.findElement(By.id(getElementProperty(""+Elementpath+""))));
			Select.selectByIndex(Index);
			break;
		case"cssSelector":
			Select = new Select (d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))));
			Select.selectByIndex(Index);
			break;
		}
		
	}

	public static void selectdropdownbytext(String locator, String Elementpath, String Text) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			Select = new Select( d.findElement(By.xpath(getElementProperty(""+Elementpath+""))));
			Select.selectByVisibleText(Text);
			break;
		case"id":
			Select = new Select (d.findElement(By.id(getElementProperty(""+Elementpath+""))));
			Select.selectByVisibleText(Text);
			break;
		case"cssSelector":
			Select = new Select (d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))));
			Select.selectByVisibleText(Text);
			break;
		}
	
	}
	
	public static void deselectdropdownbyvalue(String locator, String Elementpath, String Value) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			Select = new Select( d.findElement(By.xpath(getElementProperty(""+Elementpath+""))));
			Select.deselectByValue(Value);
			break;
		case"id":
			Select = new Select (d.findElement(By.id(getElementProperty(""+Elementpath+""))));
			Select.deselectByValue(Value);
			break;
		case"cssSelector":
			Select = new Select (d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))));
			Select.deselectByValue(Value);
			break;
		
		}
		
	}
	
	public static void deselectdropdownbyindex(String locator, String Elementpath, int Index) {
		
		WaitForElement(10,locator,Elementpath);
		
	 switch(locator) {
		
	 case"xpath":
			Select = new Select( d.findElement(By.xpath(getElementProperty(""+Elementpath+""))));
			Select.deselectByIndex(Index);
			break;
		case"id":
			Select = new Select (d.findElement(By.id(getElementProperty(""+Elementpath+""))));
			Select.deselectByIndex(Index);
			break;
		case"cssSelector":
			Select = new Select (d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))));
			Select.deselectByIndex(Index);
			break;
		
		}
		
	}

	public static void deselectdropdownbytext(String locator, String Elementpath, String Text) {
		
		WaitForElement(10,locator,Elementpath);
		
		switch(locator) {
		
		case"xpath":
			Select = new Select( d.findElement(By.xpath(getElementProperty(""+Elementpath+""))));
			Select.deselectByVisibleText(Text);
			break;
		case"id":
			Select = new Select (d.findElement(By.id(getElementProperty(""+Elementpath+""))));
			Select.deselectByVisibleText(Text);
			break;
		case"cssSelector":
			Select = new Select (d.findElement(By.cssSelector(getElementProperty(""+Elementpath+""))));
			Select.deselectByVisibleText(Text);
			break;
		
		}
	
	}
	
	public static void SelectradiobuttonbyValue(String locator,String Value) {
		
		
		switch(locator) {
		
		case"xpath":
			d.findElement(By.xpath("//input[@value='"+Value+"']//following-sibling::i")).click();
			break;
		case"id":
			d.findElement(By.xpath("//input[@value='"+Value+"']//following-sibling::i")).click();
			break;
		
		}
		
	}
	
	public static void selectcheckbox(String locator,String ID) {
		
		
		switch(locator) {
		
		case"xpath"://input[@id='chkAuthorize']//following-sibling::i
			d.findElement(By.xpath("//input[@id='"+ID+"']//following-sibling::i")).click();
			break;
		case"id":
			d.findElement(By.xpath("//input[@id='"+ID+"']//following-sibling::i")).click();
			break;
		
		}
		
	}

	public static void storeelementetext(String locator, String Elementpath) {
		
		WaitForElement(10,locator,Elementpath);
		
		
		switch(locator) {
		
		case"xpath"://input[@id='chkAuthorize']//following-sibling::i
			StoredSring = d.findElement(By.xpath(getElementProperty(""+Elementpath+""))).getText();
			break;
		case"id":
			StoredSring = d.findElement(By.id(getElementProperty(""+Elementpath+""))).getText();
			break;
		
		}
		
	}
	
	public static void Fileupload(String FileName) {
		
		WebElement element = d.findElement(By.id("fuBrowse"));
		element.sendKeys("C:\\Users\\apgandhi\\Desktop\\BDDFramework\\BDDFreamework\\UploadDocuments\\"+FileName+"");
		
	}
	
	public static void FileUpdate(String FileName, String UpdateTest) {
		
		
			
		}
	
	public static int interestmonthcalculator(int wagesubmissionyear, String quarter) {
		LocalDate currentdate = LocalDate.now();
    	int Year = currentdate.getYear();
    	int Month = 12-currentdate.getMonthValue();	
    	int wageyear = Year - wagesubmissionyear;
    	totalmonths = (wageyear*12)-Month;

    	
    	switch(quarter) {
    	
    	case"January, February, March (Q1)":
    		 Gracemonth = 8;
    		break;
    	case"April, May, June (Q2)":
    		 Gracemonth = 5;
    		break;
    	case"July, August, September (Q3)":
    		 Gracemonth = 2;
    		break;
    	case"October, November, December (Q4)":
    		 Gracemonth = -1;
    		break;
    	
    	}
    	
    	if(quarter.equals("October, November, December (Q4)") && totalmonths ==0)
    	{
    		
    		 wagetotalmonths = totalmonths+0;
        	System.out.println(wagetotalmonths);
    	}
    	else {
    		 wagetotalmonths = totalmonths+Gracemonth;
        	System.out.println(wagetotalmonths);
    	}
    	
    	return wagetotalmonths;
    
	} 
	
	public static String getTableData(String Elementpath,int RowIndex,int ColunmIndex) {
		
		Tabledata = d.findElement(By.xpath("((//div[@name='"+getElementProperty(""+Elementpath+"")+"'"
				+ " or @id='"+getElementProperty(""+Elementpath+"")+"']//child::div[@class='divTableRow'])["+RowIndex+"]//child::div)["+ColunmIndex+"]")).getText();
		
		return Tabledata;
		
		
	}
	
	
	public static void terminate() {
		d.quit();
		
	}

}


		
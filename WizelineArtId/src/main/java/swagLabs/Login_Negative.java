package swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver; 	//Please uncomment this line and comment line 6 in order to change from Chrome to Firefox browser
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Login_Negative {

	public static void main(String[] args) throws InterruptedException, IOException {

	// set ExtentReports as reporting tool
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReports_swagLabs.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		ExtentTest loginNegative = extent.createTest("Login (negative)", "Login with an invalid user");
		loginNegative.log(Status.INFO, "Starting Test Case");
		
		String exePath = "./driver/chromedriver";
		System.setProperty("webdriver.chrome.driver", exePath);
		WebDriver driver = new ChromeDriver();
		
		//Please uncomment the next 3 lines and comment lines 31 to 33 in order to change from Chrome to Firefox browser
		//String exePath = "./driver/geckodriver";
		//System.setProperty("webdriver.gecko.driver", exePath);
		//WebDriver driver = new FirefoxDriver();
		
		driver.get("https://www.saucedemo.com/");
		loginNegative.log(Status.INFO, "Navigated to https://www.saucedemo.com/");
		driver.manage().window().maximize();
	// set test data source
		FileInputStream fs = new FileInputStream("./src/test/resources/Login (negative).xlsx");
		//Creating a workbook
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		//read data as Strings and create the variables to store data retrieved from .xlsx
		DataFormatter df = new DataFormatter();
		String cellValUsr = df.formatCellValue(sheet.getRow(1).getCell(0));
		String cellValPass = df.formatCellValue(sheet.getRow(1).getCell(1));
	// login (negative)
		//Get web elements and send test data to loginNegative page
		WebElement userName = driver.findElement(By.id("user-name"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement btnloginNegative = driver.findElement(By.id("login-button"));
		userName.sendKeys(cellValUsr);
		Thread.sleep(1000);
		password.sendKeys(cellValPass);
		Thread.sleep(1000);
		btnloginNegative.click();
		loginNegative.log(Status.INFO, "Attempting to log in");		
	// loginNegative validation
		//Validate URL
		String urlActual = driver.getCurrentUrl();
		if(urlActual.equals("https://www.saucedemo.com/")) {
			System.out.println("Login failed (expected)");
			loginNegative.pass("Login failed (expected)"); 
		}else {
			System.out.println("ERROR: Url is NOT the expected one");
			loginNegative.fail("ERROR: Url is NOT the expected one");
		}
		System.out.println("Landing Url: " + urlActual);
		
	// 
		loginNegative.log(Status.INFO, "Confirming if error message is displayed");	
		try{
			driver.findElement(By.xpath("//h3[text()='Epic sadface: Username and password do not match any user in this service']"));
				System.out.println("Expected error message is displayed");
				loginNegative.pass("Loggin error message is successfully displayed"); 
			}
			catch(NoSuchElementException e){
				System.out.println("Expected error message is NOT being displayed");
				loginNegative.fail("Expected error message is NOT being displayed");
			}	
		
		Thread.sleep(1000);
		driver.close();
		loginNegative.info("Closed the browser");
			
		loginNegative.info("Test completed");
		extent.flush();
	}
}

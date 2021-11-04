package swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver; 	//Please uncomment this line and comment line 6 in order to change from Chrome to Firefox browser
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Logout {

	public static void main(String[] args) throws InterruptedException, IOException {
		
	// set ExtentReports as reporting tool
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReports_swagLabs_Logout.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		ExtentTest login = extent.createTest("Logout", "Logout from the home page");
		login.log(Status.INFO, "Starting Test Case");
		
		String exePath = "./driver/chromedriver";
		System.setProperty("webdriver.chrome.driver", exePath);
		WebDriver driver = new ChromeDriver();
		//Please uncomment the next 3 lines and comment lines 29 to 31 in order to change from Chrome to Firefox browser
		//String exePath = "./driver/geckodriver";
		//System.setProperty("webdriver.gecko.driver", exePath);
		//WebDriver driver = new FirefoxDriver();
		
		driver.get("https://www.saucedemo.com/");
		login.log(Status.INFO, "Navigated to https://www.saucedemo.com/");
		driver.manage().window().maximize();
	// set test data source
		FileInputStream fs = new FileInputStream("./src/test/resources/Login.xlsx");
		//Creating a workbook
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		//read data as Strings and create the variables to store data retrieved from .xlsx
		DataFormatter df = new DataFormatter();
		String cellValUsr = df.formatCellValue(sheet.getRow(1).getCell(0));
		String cellValPass = df.formatCellValue(sheet.getRow(1).getCell(1));
	// login
		//Get web elements and send test data to login page
		WebElement userName = driver.findElement(By.id("user-name"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement btnLogin = driver.findElement(By.id("login-button"));
		userName.sendKeys(cellValUsr);
		Thread.sleep(1000);
		password.sendKeys(cellValPass);
		Thread.sleep(1000);
		btnLogin.click();
		login.log(Status.INFO, "Logging in");		
	// login validation
		String title = driver.getTitle();
		System.out.println(title);
		//Validate URL
		String urlActual = driver.getCurrentUrl();
		if(urlActual.equals("https://www.saucedemo.com/inventory.html")) {
			System.out.println("User logged in successfully");
			login.pass("User logged in successfully"); 
		}else {
			System.out.println("Login Failed: Logged in Url is NOT the expected one");
			login.fail("Login Failed: Logged in Url is NOT the expected one.");
		}
		System.out.println("Landing Url: " + urlActual);
		
	//Logout
		WebElement btnMenu = driver.findElement(By.id("react-burger-menu-btn"));
		btnMenu.click();
		Thread.sleep(1000);
		WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));
		logoutLink.click();
		Thread.sleep(1000);
		
		String urlLogout = driver.getCurrentUrl();		
		if(urlLogout.equals("https://www.saucedemo.com/")) {
			System.out.println("User logged out successfully");
			login.pass("User logged out successfully"); 
		}else {
			System.out.println("ERROR: User was not logged out successfully");
			login.fail("User was not logged out successfully.");
		}
		System.out.println("Logout Url: " + urlLogout);

		Thread.sleep(1000);
		driver.close();
		login.info("Closed the browser");
			
		login.info("Test completed");
		extent.flush();
	}
}

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

public class Purchase {

	public static void main(String[] args) throws InterruptedException, IOException {
	// set ExtentReports as reporting tool
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReports_swagLabs_Purchase.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		ExtentTest login = extent.createTest("Purchase", "Complete a purchase");
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
		FileInputStream fs = new FileInputStream("./src/test/resources/Purchase.xlsx");
		//Creating a workbook
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		//read data as Strings and create the variables to store data retrieved from .xlsx
		DataFormatter df = new DataFormatter();
		String cellValUsr = df.formatCellValue(sheet.getRow(1).getCell(0));
		String cellValPass = df.formatCellValue(sheet.getRow(1).getCell(1));
		String firstName = df.formatCellValue(sheet.getRow(1).getCell(2));
		String lastName = df.formatCellValue(sheet.getRow(1).getCell(3));
		String postalCode = df.formatCellValue(sheet.getRow(1).getCell(4));
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
		String urlActual = driver.getCurrentUrl();
		if(urlActual.equals("https://www.saucedemo.com/inventory.html")) {
			System.out.println("User logged in successfully. Products page is displayed");
			login.pass("User logged in successfully. Products page is displayed"); 
		}else {
			System.out.println("Login Failed: Products page is NOT displayed");
			login.fail("Login Failed: Products page is NOT displayed");
		}
		System.out.println("Landing Url: " + urlActual);
		
	// adding any item	
		WebElement addAnyItem = driver.findElement(By.xpath("//button[text()='Add to cart']"));
		Thread.sleep(1000);
		addAnyItem.click();
		Thread.sleep(1000);
		WebElement shopCart = driver.findElement(By.className("shopping_cart_badge"));
		shopCart.click();
		WebElement checkout = driver.findElement(By.id("checkout"));
		checkout.click();
		Thread.sleep(1000);
		WebElement fName = driver.findElement(By.id("first-name"));
		fName.click();
		Thread.sleep(1000);
		fName.sendKeys(firstName);
		WebElement lName = driver.findElement(By.id("last-name"));
		lName.click();
		Thread.sleep(1000);
		lName.sendKeys(lastName);
		WebElement pCode = driver.findElement(By.id("postal-code"));
		pCode.click();
		Thread.sleep(1000);
		pCode.sendKeys(postalCode);
		WebElement continueBtn = driver.findElement(By.id("continue"));
		continueBtn.click();
		Thread.sleep(1000);
		WebElement finish = driver.findElement(By.id("finish"));
		finish.click();
		Thread.sleep(1000);
		
		try{
			driver.findElement(By.className("pony_express"));
				System.out.println("Purchase is completed successfully");
				login.pass("Purchase is completed successfully"); 
			}
			catch(NoSuchElementException e){
				System.out.println("Purchase is NOT completed");
				login.fail("Purchase is NOT completed");
			}		
		
		Thread.sleep(1000);
		driver.close();
		login.info("Closed the browser");
			
		login.info("Test completed");
		extent.flush();
	}
}

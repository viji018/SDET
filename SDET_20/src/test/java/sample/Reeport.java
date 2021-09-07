package sample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.vtiger.comcast.genericUtility.ExcelUtility;
import com.vtiger.comcast.genericUtility.FileUtility;
import com.vtiger.comcast.genericUtility.JavaUtility;
import com.vtiger.comcast.genericUtility.WebDriverUtility;
import com.vtiger.comcast.pomrepositylib.Home;
import com.vtiger.comcast.pomrepositylib.Login;

public class Reeport  {
	public WebDriver driver;
	  /*Object Creation for Lib*/
		public JavaUtility jLib = new JavaUtility();
		public WebDriverUtility wLib = new WebDriverUtility();
		public FileUtility fLib = new FileUtility();
		public ExcelUtility eLib = new ExcelUtility();
		
		public	ExtentHtmlReporter reporter;
		public	ExtentReports reports;
		public ExtentTest test;
		
	@BeforeSuite(groups = { "smokeTest","regressionTest"})
	public void configBS() {
		
		reporter = new ExtentHtmlReporter("./SDET_20/ExtentReports/SDET21.html");
		System.out.println("before suite executed");
		reporter.config().setDocumentTitle("SDET");
		reporter.config().setTheme(Theme.DARK);
		reports = new ExtentReports();
		reports.attachReporter(reporter);
		System.out.println("========================connect to DB========================");
	}
		
	@BeforeClass(groups = { "smokeTest","regressionTest"})
	public void configBC() throws Throwable {
		System.out.println("=============Launch the Browser=======");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		this.driver = driver; 
		
	}
/*	@Parameters("BROWSER")
	@BeforeClass
	public void configBC(String BROWSER) {
		System.out.println("=============Launch the Browser=======");
		if(BROWSER.equals("chrome")) {
		    driver = new ChromeDriver();
		}else if(BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		}else if(BROWSER.equals("ie")) {
			driver = new InternetExplorerDriver();
		}else {
			 driver = new ChromeDriver();
		}
		wLib.waitUntilPageLoad(driver);
		driver.manage().window().maximize();
	}*/

	
	@BeforeMethod(groups = { "smokeTest","regressionTest"})
	public void configBM() throws Throwable {
		/*common Data*/
		String URL = fLib.getPropertyKeyValue("url");
		String USERNAME = fLib.getPropertyKeyValue("username");
		String PASSWORD = fLib.getPropertyKeyValue("password");
		String BROWSER = fLib.getPropertyKeyValue("browser");
		/* Navigate to app*/
		
	
		   driver.get(URL);
	        /* step 1 : login */
	        Login loginPage = new Login(driver);
	        loginPage.LoginToApp(USERNAME, PASSWORD);
	}
	
	
	@AfterMethod(groups = { "smokeTest","regressionTest"})
	public void configAM() {
	      /*step 6 : logout*/
		Home homePage = new Home(driver);
      homePage.logout();
	}
	
	@AfterClass(groups = { "smokeTest","regressionTest"})
	public void configAC() {
		System.out.println("=============Close the Browser=======");
		driver.quit();
	}
	
	@AfterSuite(groups = { "smokeTest","regressionTest"})
	public void configAS() {
		System.out.println("========================close DB========================");
		reports.flush();
	}
	
	@Test
	public void smaple1 () {
		test = reports.createTest("smaple1");
		SoftAssert soft = new SoftAssert();
		
		Home home = new Home(driver);
		
		
		
		String expectedTitle = "Hom";
		String actualTitle = driver.findElement(By.linkText("Home")).getText();
		soft.assertEquals(actualTitle, expectedTitle);
		soft.assertAll();
		System.out.println("test 1 is pass");
		
		/*if(actualTitle.equals(expectedTitle)) {
			System.out.println("PASS:"+ actualTitle +" succesfully displayed");
		}
		else {
			System.out.println("FAIL:"+ actualTitle +" Not displayed");
			
		}*/
		

	}
	@Test
	public void smaple2 () {
		test = reports.createTest("smaple2");
		
		
		Home home = new Home(driver);
		
		
		home.getOrganizationLnk().click();
		String expectedTitle = "Organizations";
		String actualTitle = driver.findElement(By.linkText("Organizations")).getText();
		Assert.assertEquals(actualTitle, expectedTitle);
		System.out.println("test 2 is pass");
		

	}

}

package com.nopCommerce.com.nopCommerce;

import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Assert.ThrowingRunnable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public  class ExtentReporting {
	public WebDriver driver;
	public ExtentHtmlReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest test;
	
    @BeforeTest
	public void setExtent()
	{
    	//C:\Users\Vijay\eclipse-workspace\com.nopCommerce\Reports
		
    	//htmlreporter=new ExtentHtmlReporter(System.getProperty(("user.dir")+"\\test-output\\vijay.html"));
    	htmlreporter=new ExtentHtmlReporter("C:\\Users\\Vijay\\eclipse-workspace\\com.nopCommerce\\reports\\vijay.html");

		htmlreporter.config().setDocumentTitle("Automation report");
		htmlreporter.config().setReportName("Functional report");
		htmlreporter.config().setTheme(Theme.DARK);
    	
		extent=new ExtentReports();
		extent.attachReporter(htmlreporter);
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("HostName", "localhost");
		extent.setSystemInfo("Operating System", "Windows");
		extent.setSystemInfo("Tester", "Vijay");
		
		
	}
    @AfterTest
    public void endtest()
    {
    	extent.flush();
    }
   
    
    @BeforeMethod
    public void setUp()
    {
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vijay\\Downloads\\chromedriver_win32 (1)//chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/");
	}
    @Test
    public void  nopCommerceTitleTest()
    {
      test=extent.createTest("nopCommerceTitleTest");
      String title=	driver.getTitle();
      System.out.println(title);
      Assert.assertEquals(title, "nopCommerce demo store123");
     
      
    }
    @Test
    public void nopCommerceLogoTest()
    {
    	test = extent.createTest("nopCommerceLogoTest");
    	boolean status=driver.findElement(By.xpath("//img[@alt='nopCommerce demo store']")).isDisplayed();
    	Assert.assertTrue(status);
     }
    @Test
    public void nopCommerceLoginTest()
    {
    	test=extent.createTest("nopCommerceLoginTest");
    	driver.findElement(By.cssSelector("a[href*='/login']")).click();
    	String loginTitle=driver.findElement(By.xpath("//div/h1")).getText();
    	Assert.assertEquals(loginTitle, "Welcome, Please Sign In!");
    }
    @AfterMethod
     public void tearDown(ITestResult result) throws Throwable 
     {
    	 
    	if( result.getStatus()==ITestResult.FAILURE)
    	{
    	    test.log(Status.FAIL,"TEST CASE FAILED IS"+result.getName());
    	    test.log(Status.FAIL, "TEST CASE FAILED IS"+result.getThrowable());
    	 String SceenshotPath= ExtentReporting.getScreenShotPath(driver, result.getName());
    	 test.addScreenCaptureFromPath(SceenshotPath);
    	 
    	}
    	else if(result.getStatus()==ITestResult.SKIP)
    	{
    		test.log(Status.SKIP, "Test case is skipped"+result.getName());
    	}
    	else if(result.getStatus()==ITestResult.SUCCESS)
    	{
    		test.log(Status.PASS, "Test is passed"+result.getName());
    	}
    	 driver.quit();
     }
     
     public static  String getScreenShotPath(WebDriver driver,String ScreenshotName) throws IOException
     {
    	// String date= new SimpleDateFormat("yyyyMMddhhmmss").format(new (Date));
    	TakesScreenshot ts=(TakesScreenshot) driver;
    	File source= ts.getScreenshotAs(OutputType.FILE);
    	String destination=System.getProperty(("user.dir")+"\\Screenshot\\"+ScreenshotName+".png");
    	
    	File Finaldestination=new File(destination);
    	FileUtils.copyFile(source, Finaldestination);
    	return destination;
    	
    	 
    	 
     }
    
	
}

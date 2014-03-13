package screenshot.rule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ExampleTests {

	public static WebDriver webDriver;
	
	@Before
	public void init(){
		ChromeOptions options = new ChromeOptions();
// 		this commented line is how to load an unpacked extension of chrome - this one called "XPath Helper"
//      which is one of my favorites to use		
//		options.addArguments("load-extension=C:\\Users\\Erez.Levy\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\hgimnogjllphhhkhlmebbmlgjoejdpjl\\1.0.13_0\\");
		options.addArguments("--start-maximized");	
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		webDriver = new ChromeDriver(options);
	}
	
	@Rule
	public ScreenshotTestRule screenshotRule = new ScreenshotTestRule();
	
	@Test
	@OnFailScreenShot
	public void testOne() throws Exception {
		screenshotRule.setDriver(webDriver);
		webDriver.get("http://www.google.com");
		WebElement webElement = webDriver.findElement(By.cssSelector("div#searchform input[type=\"text\"]"));
		webElement.sendKeys("Top-Q");
		webElement.submit();
		throw new Exception("Top-Q was searched ?");
	}
	
	@After
	public void wrapUp(){
//		webDriver.quit();
	}
}

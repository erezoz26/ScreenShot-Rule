package screenshot.rule;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotTestRule implements TestRule{
	
	private WebDriver driver;
			
    public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public ScreenshotTestRule(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public ScreenshotTestRule() {
		// TODO Auto-generated constructor stub
	}

	public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                	if(description.getAnnotation(OnFailScreenShot.class) != null){
                		String path = captureScreenshot(description.getMethodName(),t.getMessage());
                		System.out.println(path);
                	}
                    throw t; // rethrow to allow the failure to be reported to JUnit
                }
            }

			public String captureScreenshot(String fileName,String insideScreenshotDescription) {
				String path = "";
				// get your driver instance				
				try {	
					((JavascriptExecutor) driver)
							.executeScript("document.body.innerHTML +='<div style=\"word-wrap: break-word;position:absolute;"
									+ "max-width:'+ window.innerWidth/2 +'px;max-height:'+ window.innerHeight/2 +'px;top:'+ window.pageYOffset +'px;left:0px;"
									+ "opacity:0.6;z-index:999;background:#DDD;color:red;font-size:20px\">"
									+ insideScreenshotDescription
									+ "</div>';");
					File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String dateNow = formatter.format(currentDate.getTime());
					String snapShotDirectory = "c:\\ScreenshotsFolder\\";
					File mainFolder = new File(snapShotDirectory);
					if (mainFolder.mkdir()) {
						System.out.println("New Folder Created");
					}
					File subFolder = new File(snapShotDirectory + dateNow + "\\");
					if (subFolder.mkdir()) {
						System.out.println("New Folder Created");
					}
					source.renameTo(new File(snapShotDirectory + dateNow + "\\" + fileName + ".png"));
					path = snapShotDirectory + dateNow + "\\" + fileName + ".png";

				} catch (Exception e) {
					path = "Failed to capture screenshot: " + e.getMessage();
				}
				return path;
			}
        };
    }
}

package scrapingData;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	WebDriver driver;

	@BeforeClass
	public void startBrowser() throws Exception {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.qualitymarine.com/quality-marine/fish/angels/");
		Thread.sleep(3000);
	}

	@AfterClass
	public void closeBrowser() {
		driver.close();
	}

}

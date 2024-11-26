package scrapingData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ScrapQuelityMarine extends BaseClass {

	@Test
	public void test() throws IOException {

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Fish Data");

		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Date");
		headerRow.createCell(1).setCellValue("Page Title");
		headerRow.createCell(2).setCellValue("Fish Information");
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		int rowNum = 1;
		int index = 1;

		while (true) {
			try {

				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//main//a)[" + index + "]")));
				WebElement linkElement = driver.findElement(By.xpath("(//main//a)[" + index + "]"));
				String linkText = linkElement.getText();

				linkElement.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//h1[1]")));
				WebElement pageTitleElement = driver.findElement(By.xpath("//div//h1[1]"));
				String pageTitle = pageTitleElement.getText();

				List<WebElement> fishDataElements = driver.findElements(By.xpath("//div[@class='accordion-item']"));

				for (WebElement fishData : fishDataElements) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(currentDate);
					row.createCell(1).setCellValue(pageTitle);
					row.createCell(2).setCellValue(fishData.getText());
				}
				driver.navigate().back();
				index++;
			} catch (Exception e) {
				System.out.println("No more links found at index: " + index);
				break;
			}
		}
		try (FileOutputStream fileOut = new FileOutputStream("FishData.xlsx")) {
			workbook.write(fileOut);
		}
		workbook.close();
		System.out.println("All data has been written to FishData.xlsx");
	}
}

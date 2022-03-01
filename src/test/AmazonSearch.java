package test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AmazonSearch {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","geckodriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "admin");
		Statement stm=con.createStatement();
		ResultSet result=stm.executeQuery("Select name from eProduct where ID=5");
		while(result.next()) 
		{
			String searchText=result.getString("name");
			
			WebElement searchbox=driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
			searchbox.sendKeys(searchText);
			
			WebElement searchbutton=driver.findElement(By.xpath("//input[@value='Go']"));
			searchbutton.click();
			
			List<WebElement> iPhoneName=driver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
			System.out.println("Count of Results: "+iPhoneName.size());
			List<WebElement> Price=driver.findElements(By.xpath("//div[@class='a-section']//span[@class='a-price-whole']"));
			System.out.println("\n******Search Results******\n");
			for(int i=0;i<=iPhoneName.size()-1;i++) {
				
				if(iPhoneName.get(i).getText().startsWith("Apple iPhone 13")) {
					
					System.out.println("iPhone Name: "+iPhoneName.get(i).getText()+"  Price: "+Price.get(i).getText());
				}
				
			}	
		
		
		}
		WebElement scrollElement=driver.findElement(By.xpath("//*[starts-with(text(),'RESULTS')]"));
		JavascriptExecutor obj1 = (JavascriptExecutor) driver;
		obj1.executeScript("arguments[0].scrollIntoView();", scrollElement);
		
		TakesScreenshot object1=(TakesScreenshot) driver;
		File file1=object1.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file1, new File("AmazonProject.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.close();
	}

}

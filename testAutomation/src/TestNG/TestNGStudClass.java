package TestNG;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class TestNGStudClass {
  WebDriver driver = null;
  
  @BeforeClass
  public void openApplication() {
	  System.setProperty("webdriver.gecko.driver","C:\\users\\dmali\\Downloads\\geckodriver\\geckodriver.exe");
	  driver = new FirefoxDriver();
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driver.navigate().to("http://127.0.0.1:5000/");
      driver.manage().window().maximize();
  }
  
  @AfterMethod
  public void waitForResult() throws InterruptedException{
	  Thread.sleep(9000);
  }

  @Test(priority=1)
  public void testListStudent(){
	  driver.get("http://127.0.0.1:5000/list");
      String strUrl = driver.getCurrentUrl();
      Assert.assertEquals(strUrl, "http://127.0.0.1:5000/list");
  }
  
  //@Test(dependsOnMethods = { "testListStudent" })
  @Test(priority=2)
  public void testAddStudent(){
	  driver.get("http://127.0.0.1:5000/add");
      driver.findElement(By.id("name")).sendKeys("Dhananjay");
      driver.findElement(By.id("submit")).click();
      String strUrl = driver.getCurrentUrl();
      Assert.assertEquals(strUrl, "http://127.0.0.1:5000/list");
  }

  @Test(priority=3)
  public void testAddStudentFather(){
	  driver.get("http://127.0.0.1:5000/add_father");
	  driver.findElement(By.id("name")).sendKeys("Ashok");
	  driver.findElement(By.id("stu_id")).sendKeys("18");
      driver.findElement(By.id("submit")).click();
      String strUrl = driver.getCurrentUrl();
      Assert.assertEquals(strUrl, "http://127.0.0.1:5000/list");
  }
  
  @Test(priority=4)
  public void testDeleteStudentNegative(){
	  driver.get("http://127.0.0.1:5000/delete");
	  driver.findElement(By.id("id")).sendKeys("10");
      driver.findElement(By.id("submit")).click();
      String strUrl = driver.getCurrentUrl();
      Assert.assertEquals(strUrl, "http://127.0.0.1:5000/delete");
  }

  @Test(priority=5)
  public void testDeleteStudentPositive(){
	  driver.get("http://127.0.0.1:5000/delete");
	  driver.findElement(By.id("id")).sendKeys("12");
      driver.findElement(By.id("submit")).click();
      String strUrl = driver.getCurrentUrl();
      Assert.assertEquals(strUrl, "http://127.0.0.1:5000/list");
  }
  
  @AfterTest
  public void clearMemory() {
	  System.gc();
  }
  
  @AfterClass
  public void closeApplication(){
	  driver.close();
  }
}

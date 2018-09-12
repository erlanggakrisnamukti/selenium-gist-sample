package gist;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.json.*;

public class main {
	
	public WebDriver firefoxDriver;
	String loginGitURL;
	String gistGitURL;
	String gistAccountGitURL;
	
	String _username;
	String _password;
	
	JSONObject configObj;
	
	@BeforeTest
	public void Setup() {
		System.setProperty("webdriver.gecko.driver", "/home/erlangga/Erlangga/Libs/geckodriver");
		firefoxDriver = new FirefoxDriver();
	    loginGitURL = "https://github.com/login";
	    gistGitURL = "https://gist.github.com/";
	    gistAccountGitURL = "https://gist.github.com/erlanggak/";
	    
	    final File classpathRoot = new File(System.getProperty("user.dir"));
	    String path = classpathRoot + "/src/test/resources/configuration/config.json";
	    try {
			Scanner config = new Scanner(new FileReader(classpathRoot + "/src/test/resources/configuration/config.json"));
		
		    System.out.println(classpathRoot + "/src/test/resources/configuration/config.json");
		    
		    configObj = new JSONObject(readFile(path, Charset.defaultCharset()));
		    System.out.println(configObj.toString());
		    
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @Test(priority=0)
    public void Login() {
    	firefoxDriver.get(loginGitURL);
    	firefoxDriver.manage().window().maximize();
    	
    	WebElement tbUsername = firefoxDriver.findElement(By.id("login_field"));
    	tbUsername.clear();
    	try {
			tbUsername.sendKeys(configObj.get("username").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	WebElement tbPassword = firefoxDriver.findElement(By.id("password"));
    	tbPassword.clear();
    	try {
			tbPassword.sendKeys(configObj.get("username").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	WebElement btnSignIn = firefoxDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/form/div[3]/input[3]"));
    	btnSignIn.click();
    }
    
    @Test(priority=1)
    public void CreateGist() {
    	firefoxDriver.get(gistGitURL);
    	
    	WebElement btnNewGist = firefoxDriver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/ul/li[1]/a"));
    	btnNewGist.click();
    	
    	WebElement tbGistFilename = firefoxDriver.findElement(By.id("/html/body/div[4]/div/div/div[2]/div[1]/form/div/div[1]/div[2]/div/div[1]/div[2]/input[2]"));
    	tbGistFilename.clear();
    	tbGistFilename.sendKeys("test");
   
    	WebElement tbGistContent = firefoxDriver.findElement(By.id("/html/body/div[4]/div/div/div[2]/div[1]/form/div/div[1]/div[2]/div/div[2]/div/div[5]/div[1]/div/div/div/div[5]/div/pre"));
    	tbGistContent.clear();
    	tbGistContent.sendKeys("asd");
    	
    	WebElement btnCreatePublicGist = firefoxDriver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div[1]/form/div/div[2]/button[1]"));
    	btnCreatePublicGist.click();
    }
    
    @Test(priority=2)
    public void EditGist() {
    	firefoxDriver.get(gistAccountGitURL);
    	
    	WebElement btnFirstListGist = firefoxDriver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div[1]/div[1]/div[2]/a"));
    	btnFirstListGist.click();
    	
    	WebElement btnEditGist = firefoxDriver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div/div[1]/ul/li[1]/a"));
    	btnEditGist.click();
    	
    	WebElement tbGistContent = firefoxDriver.findElement(By.id("/html/body/div[4]/div/div/div[2]/div[1]/form/div/div[1]/div[2]/div/div[2]/div/div[5]/div[1]/div/div/div/div[5]/div/pre"));
    	tbGistContent.clear();
    	tbGistContent.sendKeys("qwe");
    	
    	WebElement btnUpdatePublicGist = firefoxDriver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div[1]/form/div/div[2]/button"));
    	btnUpdatePublicGist.click();
    }
    
    @Test(priority=3)
    public void DeleteGist() {
    	firefoxDriver.get(gistAccountGitURL);
    	
    	WebElement btnFirstListGist = firefoxDriver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div[1]/div[1]/div[2]/a"));
    	btnFirstListGist.click();
    	
    	WebElement btnDeleteGist = firefoxDriver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div/div[1]/ul/li[2]/form/button"));
    	btnDeleteGist.click();
    	
    	WebDriverWait wait = new WebDriverWait(firefoxDriver, 10);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = firefoxDriver.switchTo().alert();
        alert.accept();
    }
    
    @Test(priority=4)
    public void ListGist() {
    	firefoxDriver.get(gistAccountGitURL);
    	
    	WebElement btnAllGist = firefoxDriver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div/div[2]/nav/a/span"));
    	String textAllGistCounter = btnAllGist.getText();
    	
    	Assert.assertEquals(textAllGistCounter, "0");
    }
    
    private static String readFile(String path, Charset encoding) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
}

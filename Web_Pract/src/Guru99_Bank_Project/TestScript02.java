package Guru99_Bank_Project;

//import org.testng.annotations.Test;

/*
 * Time to create a more professional Script
 * 1) All parameters will will be saved in File Util.java - Helps in easy code maintenance
 * 2) We will move the code to launch Webdriver in a separate method as SetUp. Helps in code understanding
 */



import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;




public class TestScript02 {

	static WebDriver driver; // Selenium control driver
    private static String baseUrl; // baseUrl of Website Guru99
    
    // This method SetUp will read initialization parameters from the class Util.java & launch Firefox 

    public static void setUp() throws Exception {
	/*
	 * Tells the Selenium client library to connect to the Webdriver
	 * service using firefox
	 * 
	 * In some PC's, Selenium can not find the binary file of Firefox because
	 * user doesn't install Firefox at its default location. We need to tell
	 * Selenium where the firefox.exe is
	 */
		File pathToBinary = new File(TestScript02Util.FIREFOX_PATH);
		@SuppressWarnings("unused")
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);

	/*
	 * Create new firefoxProfile for Testing
	 * 
	 * A profile in Firefox is a collection of bookmarks, browser settings,
	 * extensions, passwords, and history; in short, all of your personal
	 * settings. Firefox uses a DEFAULT profile to store all of your
	 * personal settings.
	 * 
	 * In this case, we use Firefox for "testing" purpose not as an "end user".
	 * We need to create NEW firefoxProfile because we want to separate the
	 * Firefox profile for testing purpose and that of an end user. If
	 * something wrong happens with the testing, you still have your DEFAULT
	 * profile to fall back to (your personal data still safe).
	 */
		
		//FirefoxProfile firefoxProfile = new FirefoxProfile();
		System.setProperty("webdriver.gecko.driver","D:\\Archana Data\\Browsers\\geckodriver-v0.26.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();

	// Setting Base URL of website Guru99
	baseUrl = TestScript02Util.BASE_URL;
	// Specifies the amount of time the driver should wait when searching for an element if it is not immediately present.
	// Refer - http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/WebDriver.Timeouts.html
	driver.manage().timeouts()
		.implicitlyWait(TestScript02Util.WAIT_TIME, TimeUnit.SECONDS);
	// Go to http://www.demo.guru99.com/V4/
	driver.get(baseUrl + "/V4/");
    }

    /**
     * 
     *        This method will perform following Test Steps
     *        
     *        1)  Go to http://www.demo.guru99.com/V4/
              2) Enter valid UserId
              3) Enter valid Password
              4) Click Login
              5) Verify the Page Title after login
     */
   
   public static void main(String[] args) throws Exception {

     	
	
    /*String username, password;
	String actualBoxtitle;*/
	   String actualTitle;

	    
	    //Setup Firefox driver
	    setUp();
	
	   
	    driver.findElement(By.name("uid")).clear(); // Good practice to clear a field before use
	    driver.findElement(By.name("uid")).sendKeys(TestScript02Util.USER_NAME);  // Enter username

	   
	    driver.findElement(By.name("password")).clear(); // Good practice to clear a field before use
	    driver.findElement(By.name("password")).sendKeys(TestScript02Util.PASSWD);  // Enter Password

	    // Click Login
	    driver.findElement(By.name("btnLogin")).click();

	  	actualTitle = driver.getTitle();
		if (actualTitle.contains(TestScript02Util.EXPECT_TITLE)) {
				    System.out.println("Test case: Passed");
		} 
		else {
				    System.out.println("Test case : Failed");
		}
				
	    driver.close();
	    
	

    }

}

        


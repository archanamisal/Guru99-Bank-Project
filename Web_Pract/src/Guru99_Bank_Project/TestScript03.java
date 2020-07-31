package Guru99_Bank_Project;


/*
Script for Day 3
 */




import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;

/*
 *         This script uses Excel to parameterize the Script
 */

public class TestScript03 {

	static WebDriver driver; // Selenium control driver
	private static String baseUrl; // baseUrl of Website Guru99

	public static void setUp() throws Exception {
		File pathToBinary = new File(TestScriptUtil03.FIREFOX_PATH);
		@SuppressWarnings("unused")
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		//FirefoxProfile firefoxProfile = new FirefoxProfile();
		System.setProperty("webdriver.gecko.driver","D:\\Archana Data\\Browsers\\geckodriver-v0.26.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = TestScriptUtil03.BASE_URL;
		driver.manage().timeouts()
		.implicitlyWait(TestScriptUtil03.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(baseUrl + "/V4/");
	}

	/**
	 * SS1: Enter valid userid & password 
	 *      Expected: Login successful home page shown 
	 * SS2: Enter invalid userid & valid password 
	 * SS3: Enter valid userid & invalid password 
	 * SS4: Enter invalid userid & invalid password 
	 *      Expected: A pop-up “User or Password is not valid” is shown
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Read test data from excel file
		// Method 	getDataFromExcel is defined in class Util
		String[][] testData = TestScriptUtil03.getDataFromExcel(TestScriptUtil03.FILE_PATH,
				TestScriptUtil03.SHEET_NAME, TestScriptUtil03.TABLE_NAME);
		String username, password;
		String actualTitle;
		String actualBoxtitle;

		//Testing for all parameters stored in the Excel File
		for (int i = 0; i < testData.length; i++) {
			username = testData[i][0]; // get username
			password = testData[i][1]; // get password

			//Setup Firefox driver
			setUp();
			// Enter username
			driver.findElement(By.name("uid")).clear();
			driver.findElement(By.name("uid")).sendKeys(username);

			// Enter Password
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys(password);

			// Click Login
			driver.findElement(By.name("btnLogin")).click();

			/* Determine Pass Fail Status of the Script
			 * If login credentials are correct,  Alert(Pop up) is NOT present. An Exception is thrown and code in catch block is executed	
			 * If login credentials are invalid, Alert is present. Code in try block is executed 	    
			 */
			try{ 

				Alert alt = driver.switchTo().alert();
				actualBoxtitle = alt.getText(); // get content of the Alter Message
				alt.accept();
				if (actualBoxtitle.contains(TestScriptUtil03.EXPECT_ERROR)) { // Compare Error Text with Expected Error Value
					System.out.println("Test case SS[" + i + "]: Passed"); 
				} else {
					System.out.println("Test case SS[" + i + "]: Failed");
				}
			}    
			catch (NoAlertPresentException Ex){ 
				actualTitle = driver.getTitle();
				// On Successful login compare Actual Page Title with Expected Title
				if (actualTitle.contains(TestScriptUtil03.EXPECT_TITLE)) {
					System.out.println("Test case SS[" + i + "]: Passed");
				} else {
					System.out.println("Test case SS[" + i + "]: Failed");
				}

			} 
			driver.close();
		} 


	}


}

package testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.Utilities;

public class Login extends Base {

	WebDriver driver;
	
	private static final Logger logger = LogManager.getLogger(Login.class);
	
	

	public Login() {
		super();
	}

	@BeforeMethod
	public void setup() {

		
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		logger.info("Browser launched");
		HomePage homePage = new HomePage(driver);
		logger.info("Navigated to HomePage");

		homePage.clickMyAccount();
		homePage.selectLoginOption();
		logger.info("MyAccount Dropdown Clicked and LoginOption Selected");

	}

	@AfterMethod
	public void teardown() {
		driver.quit();
		logger.info("Browser closed");
	}

	@Test(priority = 1, dataProvider = "validCredentialSupplier")
	public void verifyLoginWithValidCredentials(String email, String password) {

		LoginPage loginPage = new LoginPage(driver);
		logger.info("Navigate to Login Page");
		loginPage.enterEmailAddress(email);
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();
		logger.info("valid email and password entered and click Login Button");

		AccountPage accountPage = new AccountPage(driver);
		logger.info("Navigate to Account Page");

		
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(), "Edit not displayed");
		logger.info("Login test passed.");
	}

	@DataProvider(name = "validCredentialSupplier")
	public Object[][] supplyTestData() {

//		Object[][] data= {{"arunmotooricap9@gmail.com","12345"},{"arunmotooricap1@gmail.com","12345"},{"arunmotooricap3@gmail.com","12345"}};

		Object[][] data = Utilities.getTestDataFromExcel("Login");
		return data;
	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();

		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning Message is not Displayed");

	}

	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailAndValidPassword() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();

		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning Message is not Displayed");

	}

	@Test(priority = 4)
	public void verifyLoginWithValidEmailAndInvalidPassword() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmailAddress(prop.getProperty("validEmail"));
		loginPage.enterPassword(dataProp.getProperty("invalidPassword")); 
		loginPage.clickOnLoginButton();

		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning Message is not Displayed");

	}

	@Test(priority = 5)
	public void verifyLoginWithoutProvidingCredentials() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.clickOnLoginButton();

		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning Message is not Displayed");

	}

//	public String generateTimeStamp() {
//		Date date=new Date();
//		return date.toString().replace(" ", "_").replace(":", "_");
//	}

}

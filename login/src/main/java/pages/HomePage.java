package pages;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	WebDriver driver;

	// objects

	@FindBy(xpath = "/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[2]/a[1]")
	private WebElement myAccountDropMenu;

	@FindBy(linkText = "Login")
	private WebElement LoginOption;
	
	@FindBy(linkText="Register")
	private WebElement RegisterOption;
	
	@FindBy(name="search")
	private WebElement searchBoxField;
	
	@FindBy(xpath="//div[@id='search']/descendant::button")
	private WebElement searchButton;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// Action
	
	public void enterProductIntoSearchBoxField(String productText) {
        searchBoxField.sendKeys(productText);
	
}
	
	
	public void clickOnSearchButton() {
		searchButton.click();
	}
	
	public void clickMyAccount() {
		myAccountDropMenu.click();

	}
	
	

	public void selectLoginOption() {
		LoginOption.click();
	}
	
	public void selectRegisterOption() {
		RegisterOption.click();
	}
}


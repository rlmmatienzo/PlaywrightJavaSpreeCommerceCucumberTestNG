package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import java.io.FileWriter;
public class HomePage {

	protected Browser browser;
	protected Page page;

	//Locators icon lists home page
	Locator profileBtn = page.locator("//div[@data-controller='modal']/following-sibling::div[@class='hidden lg:flex']"); 
	Locator favButton = page.getByTestId("wishlist-icon");
	Locator cartButton = page.getByTestId("cart-icon");
	
	//SignUp tab
	Locator emailTxtBox = page.getByTestId("user_email");
	Locator passwordTxtBox = page.getByTestId("user_password");
	Locator pwConfirmTxtBox = page.getByTestId("user_password_confirmation");
	Locator signUpSelectBtn = page.locator("//form[@id='new_user']/following-sibling::div//a[contains(text(),'Sign Up')]"); 
	Locator signUpBtn = page.locator("//input[@value='Sign Up']");
	Locator loginBtn = page.locator("//input[@value='Login']");

	
	//SignUp if existing email
	public void userSignUp(String email, String password)
	{
		signUpSelectBtn.click();
		emailTxtBox.fill(email);
		passwordTxtBox.fill(password);
		pwConfirmTxtBox.fill(password);
		signUpBtn.click();
	}
	
	public void userLogin(String email, String password)
	{
		emailTxtBox.fill(email);
		passwordTxtBox.fill(password);
		loginBtn.click();
		
	}

	public void userClicksLeftMenu (String menu)
	{
		Locator leftMenuOption = page.locator("//div[@data-controller='account-nav']//a[contains(text(),'"+menu+"')]");
		leftMenuOption.click();
	}
	
	public void userClicksRightMenu (String menu)
	{
		Locator rightMenuOption = page.getByText(menu);
		rightMenuOption.click();
	}

	public void userClicksLogin()
	{
		Locator loginIcon = page.locator("//a[@href='/account']");
		loginIcon.click();
	}

	
	public void userValidatesAlertMessage(String message)
	{
		Locator alertMsg = page.locator("//div[@data-controller='alert']//div//p[contains(text(),'"+message+"')]");
		alertMsg.isVisible();
	}
	
    
}

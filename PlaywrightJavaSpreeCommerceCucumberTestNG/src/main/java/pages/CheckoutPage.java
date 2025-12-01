package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.*;
import com.microsoft.playwright.FrameLocator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

public class CheckoutPage extends HomePage {

	private Page page;
	  public CheckoutPage(Page page) { 
		  this.page = page;
    }
	
	String creditCardNumber = "4242 4242 4242 4242";
	Locator saveAndContinueBtn = page.locator("//button[@name='button' and contains(text(),'Save and Continue')]");
	Locator payNowBtn = page.locator("//button[@id='checkout-payment-submit']");
	Locator firstNameTxtBox = page.locator("//input[@name='order[bill_address_attributes][firstname]']");
	Locator lastNameTxtBox = page.locator("//input[@name='order[bill_address_attributes][lastname]']");
	Locator streetAddressTxtBox = page.locator("//input[@name='order[bill_address_attributes][address1]']");
	Locator apartmentTxtBox = page.locator("//input[@name='order[bill_address_attributes][address2]']");
	Locator cityTxtBox = page.locator("//input[@name='order[bill_address_attributes][city]']");
	Locator stateDropDown = page.locator("//select[@name='order[bill_address_attributes][state_id]']");
	Locator postalCodeTxtBox = page.locator("//input[@name='order[bill_address_attributes][zipcode]']");
	Locator phoneNumberTxtBox = page.locator("//input[@name='order[bill_address_attributes][phone]']");
	
	public void userSelectsCountry(String country)
	{
		Locator selectCountryDropDown = page.locator("//select[@name='order[ship_address_attributes][country_id]']");
		selectCountryDropDown.fill(country);
		page.keyboard().press("Enter");
	}
	
	public void userInputsFirstAndLastNAme(String firstName, String lastName)
	{
		firstNameTxtBox.fill(firstName);
		lastNameTxtBox.fill(lastName);
	}
	
	public void userInputsAddressInfo(String streetAddress, String apartment, String city, String state, String postalCode)
	{
		streetAddressTxtBox.fill(streetAddress);
		apartmentTxtBox.fill(apartment);
		cityTxtBox.fill(city);
		stateDropDown.selectOption(state);
		postalCodeTxtBox.fill(postalCode);
	}
	
	public void userInputsPhoneNumber(String phoneNumber)
	{
		phoneNumberTxtBox.fill(phoneNumber);
	}
	
	public void userSelectsDeliveryMethod(String deliveryMethod)
	{
		Locator deliveryMethodBtn = page.locator("//input[@name='order[shipments_attributes][0][selected_shipping_rate_id]']/following-sibling::label[contains(text(),'"+deliveryMethod+"')]");
		deliveryMethodBtn.click();
	}
	
	public void userInputsCardInfo(String creditCardInfo, String expiryDate, String securityCode, String email, String mobileNumber, String fullName)
	{
	    FrameLocator iframeLocator = page.frameLocator("xpath=//div[@class='__PrivateStripeElement']//iframe[contains(@name,'__privateStripeFrame')]"); // Using Xpath

		Locator cardNumberTxtBox = iframeLocator.locator("//input[@name='number']");
		if(creditCardInfo.contains("default")); 
		{
			cardNumberTxtBox.fill(creditCardInfo);
		}
		cardNumberTxtBox.fill(creditCardNumber);
				
		Locator expDateTxtBox = iframeLocator.locator("//input[@name='expiry']");
		expDateTxtBox.fill(expiryDate);
		
		Locator cvcTxtBox = iframeLocator.locator("//input[@name='cvc']");
		cvcTxtBox.fill(securityCode);
				
		Locator emailTxtBox = page.locator("//input[@id='Field-linkEmailInput']");
		emailTxtBox.fill(email);
		
		Locator mobileNumberTxtBox = page.locator("//input[@id='Field-linkMobilePhoneInput']");
		mobileNumberTxtBox.fill(mobileNumber);
		
		Locator fullNameTxtBox = page.locator("//input[@id='Field-linkLegalNameInput']");
		fullNameTxtBox.fill(fullName);
	}
	
	public void userUseSamePaymentInfo()
	{
		//Uses same addrress
		Locator useBillingAddressChkBox = page.locator("//label[contains(@for,'order_ship_address_id')]//turbo-frame[contains(@id,'address_')]");
		assertThat(useBillingAddressChkBox).isVisible();
		saveAndContinueBtn.click();
		
		
	}
	
	public void userClicksSaveAndContinue()
	{
		saveAndContinueBtn.click();
	}
	
	public void userClicksPayNow()
	{
		payNowBtn.click();
	}
	
	public void userValidateOrderComplete(String name)
	{
		assertThat(page).hasURL("**/complete**"); //Validate URL contains /complete (completed order)
		assertThat(page.locator("//div[@id='checkout']")).isVisible(); //Validate checkout div is visible
		assertThat(page.locator("//p[@class='text-sm mb-1 mt-3' and contains(text(),'Order')]")).isVisible(); //Validate order text is visible
		assertThat(page.locator("//p[@class='text-sm mb-1 mt-3' and contains(text(),'Order')]//strong")).isVisible(); //Validate order number is visible
		assertThat(page.locator("//h4")).hasText(Pattern.compile("Thanks "+name+" for your order!", Pattern.CASE_INSENSITIVE)); //Validate Thank you message is displayed
		assertThat(page.locator("//h5[@class='mb-3 font-semibold pb-3 border-b font-body']/p[1]")).containsText("Your order is confirmed!"); //Validate order is confirmed message is displayed
		assertThat(page.locator("//p[@class='mb-0']")).containsText("When your order is ready, you will receive an email confirmation."); //Validate order is ready will receive email notification is displayed
		assertThat(page.locator("//span[@class='text-sm']")).containsText("Status"); //Validate status text is displayed
		assertThat(page.locator("//span[@class='badge-paid']")).containsText("Paid"); //Validate payment status text is displayed
		
		Locator orderNumber = page.locator("//p[@class='text-sm mb-1 mt-3' and contains(text(),'Order')]//strong");
		String orderNumText = orderNumber.innerText();
		System.out.println("Order Number: " + orderNumText); //Shows order number in console
	}
	
}

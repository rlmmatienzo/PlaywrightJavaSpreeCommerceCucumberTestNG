package stepdefinitions;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.*;
import utils.CommonActions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PageStepDef extends CommonActions {

	private static Playwright playwright;
	private static Browser browser;
	private static BrowserContext context;
	private static Page page;
	
	HomePage homepage;
	ProductsPage productspage;
	CheckoutPage checkoutpage;
	
	
	@Given("User launches the website")
	public void user_launch_Browser()
	{
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		context = browser.newContext();
		page = context.newPage();
		page.navigate("https://demo.spreecommerce.org/");
	}
	
	@When("User signs up for the first time")
	public void user_signs_up_first_time(List<List<String>> field) throws IOException
	{
		String username = extractValueFromAListOfListOfString("Username", field);
		String password = getRandomPwd();
		homepage.userClicksLogin();
		homepage.userSignUp(username,password);
		
		String filePath = "/PlaywrightJavaSpreeCommerceCucumberTestNG/src/test/resources/credentials/credentials.csv";
		try (FileWriter writer = new FileWriter(filePath, true)) { // true enables append mode
            writer.append(System.lineSeparator()); // Add a new line separator
            writer.append(username).append(",").append(password);
            System.out.println("Line appended successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error appending to CSV file: " + e.getMessage());
        }
	
		System.out.println("User signed up for the first time..");
	}
	

	@Then("User signed up successfully")
	public void user_signed_up()
	{
		homepage.userValidatesAlertMessage("Welcome! You have signed up successfully.");
		System.out.println("User signed up successfully..");
	}
	
	@When("User logs in using default credentials")
	public void user_logs_in(List<List<String>> field)
	{
		String filePath = "/PlaywrightJavaSpreeCommerceCucumberTestNG/src/test/resources/credentials/credentials.csv"; // Replace with your CSV file path
        String lastLine = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lastLine = line; // Keep updating lastLine with the current line
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        if (lastLine != null) {
            String[] columns = lastLine.split(","); // Assuming comma as delimiter
            if (columns.length >= 2) {
                String firstColumn = columns[0].trim();
                String secondColumn = columns[1].trim();
                System.out.println("First column of last line: " + firstColumn);
                System.out.println("Second column of last line: " + secondColumn);
            } else {
                System.out.println("The last line does not contain at least two columns.");
            }
        } else {
            System.out.println("The CSV file is empty or could not be read.");
        }
        
		homepage.userValidatesAlertMessage("Signed in successfully.");
		System.out.println("User logged in..");
	}
	
	@And("User logs out in successfully")
	public void user_logs_out()
	{
		homepage.userValidatesAlertMessage("Signed out successfully.");
		System.out.println("User logged out..");
	}
	
	@Given("User selects products {string} tab")
	public void user_selects_products_tab(String productsTab)
	{
		productspage.userSelectsTab(productsTab);
	}
	
	
	@Then("User orders an item and adds to the cart")
	public void user_orders_an_item(DataTable productDataTable)
	{
        List<List<String>> productList = productDataTable.asLists(String.class);
        
        //Selects product details
        productspage.userSelectsProduct(extractValueFromAListOfListOfString("Product", productList));
        productspage.userSelectSize(extractValueFromAListOfListOfString("Size", productList));
        productspage.userSetsQuantity(extractValueFromAListOfListOfString("Quantity", productList));
        productspage.userAddToCart();
        
        //Adds to cart after selecting
        productspage.userAddToCart();
        
        //Validates item in the cart
        productspage.userValidateItem(extractValueFromAListOfListOfString("ItemName", productList),
	               extractValueFromAListOfListOfString("Price", productList),
	               extractValueFromAListOfListOfString("Size", productList),
	               extractValueFromAListOfListOfString("Quantity", productList));
        
        user_clicks_checkout();
	}
	
	
	@And("User clicks checkout")
	public void user_clicks_checkout()
	{
		productspage.userClicksCheckout();
	}
	
	
	@Then("User inputs contact information and shipping address")
	public void user_inputs_contact_information(DataTable inputContactDataTable)
	{
        List<List<String>> contactInfoList = inputContactDataTable.asLists(String.class);

		checkoutpage.userSelectsCountry(extractValueFromAListOfListOfString("Country", contactInfoList));
		checkoutpage.userInputsFirstAndLastNAme(extractValueFromAListOfListOfString("firstName", contactInfoList),extractValueFromAListOfListOfString("lastName", contactInfoList));
		
		checkoutpage.userInputsAddressInfo(extractValueFromAListOfListOfString("StreetAndHouseNo", contactInfoList),
	               extractValueFromAListOfListOfString("Apartment", contactInfoList),
	               extractValueFromAListOfListOfString("City", contactInfoList),
	               extractValueFromAListOfListOfString("State", contactInfoList),
	               extractValueFromAListOfListOfString("PostalCode", contactInfoList));
		
		checkoutpage.userInputsPhoneNumber(extractValueFromAListOfListOfString("Country", contactInfoList));
		checkoutpage.userSelectsDeliveryMethod(extractValueFromAListOfListOfString("Country", contactInfoList));

		checkoutpage.userClicksSaveAndContinue();
	}
	
	
	@Then("User inputs payment information and places the order")
	public void user_inputs_payment_information(DataTable paymentInfoDataTable)
	{
		List<List<String>> cardInfoList = paymentInfoDataTable.asLists(String.class);
		checkoutpage.userInputsCardInfo(extractValueFromAListOfListOfString("CreditCardNo", cardInfoList),
	               extractValueFromAListOfListOfString("ExpiryDate", cardInfoList),
	               extractValueFromAListOfListOfString("SecurityCode", cardInfoList),
	               extractValueFromAListOfListOfString("Email", cardInfoList),
	               extractValueFromAListOfListOfString("MobileNumber", cardInfoList),
	               extractValueFromAListOfListOfString("FullName", cardInfoList));
		checkoutpage.userClicksPayNow();
	}
	
	
	@And("User validates the order confirmation of {string}")
	public void user_validates_order_confirmation(String name)
	{
		checkoutpage.userValidateOrderComplete(name);
	}
	
}

package pages;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductsPage extends HomePage {
	
	private Page page;
	
	  public ProductsPage(Page page) { 
		  this.page = page;
    }


        //Locator cartIconBtn = page.getByTestId("cart-icon");
    
    	public void userSelectsTab(String tab)
    	{
    		Locator selectTab = page.locator("//a[@data-title='"+tab+"']");
            selectTab.click();
    	}
        
		public void userSelectsProduct(String product)
		{
			Locator selectProduct = page.locator("//div[@class='product-card-inner']//h3[contains(text(),'"+product+"')]");
			assertThat(selectProduct).isVisible();
			selectProduct.click();
		}
		
		public void userSelectSize(String size)
		{
			Locator chooseSizeBtn = page.locator("//button//legend[contains(text(),'Please choose Size')]");
			chooseSizeBtn.click();
			Locator sizeBtn = page.locator("//div[@data-dropdown-target='menu']//p[text()='"+size+"']");
			sizeBtn.click();
		}
		
		public void userAddToCart()
		{
			Locator addToCartBtn = page.locator("//div[@data-sticky-button-target='stickyButton']//button[contains(@class,'add-to-cart-button')]");
			addToCartBtn.click();
		}
		
		public void userSetsQuantity(String quantity)
		{
			Locator itemNameTxt = page.locator("//input[@id='quantity']");
			itemNameTxt.fill(quantity);
		}
		
		public void userValidateItem(String itemName, String price, String size, String quantity)
		{
			long totalAmountValue = 0;
			
			Locator itemNameTxt = page.locator("a//[@class='font-semibold text-text' AND contains(text(),'"+itemName+"')]");
			Locator priceTxt = page.locator("//span[@class='text-danger']");
			Locator sizeTxt = page.locator("//div[@class='h-[30px] border border-default px-2 inline-flex items-center hover:border-dashed hover:border-primary text-sm' AND contains(text(),'"+size+"')]");
			Locator quantityTxt = page.locator("//input[@name='line_item[quantity]']");
			Locator totalAmountTxt = page.locator("//span[@class='shopping-cart-total-amount']");
		    
			String priceValue = priceTxt.textContent();
			String quantityValue = quantityTxt.inputValue();
		    String totalValue = totalAmountTxt.textContent();

		    totalAmountValue = Integer.parseInt(priceValue.replaceAll("[^0-9]", "")) * Integer.parseInt(quantityValue);
		   
		    assertThat(itemNameTxt).containsText(itemName);
		    assertThat(priceTxt).containsText(price);
		    assertThat(sizeTxt).containsText(size);
		    assertThat(quantityTxt).hasValue(quantity);
		    assertThat(totalAmountTxt).containsText(String.valueOf(totalAmountValue));
		}
		
		public void userClicksCheckout()
		{
	        Locator checkoutBtn = page.locator("//a[@data-cart-target='checkoutButton']");
			checkoutBtn.click();
		}
}

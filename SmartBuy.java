import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SmartBuy {
	public static String removeAfterSpace(String str) {
		int spaceIndex = str.indexOf(' ');
		if (spaceIndex != -1) {
			return str.substring(0, spaceIndex);
		} else {
			return str;
		}
	}

	WebDriver driver = new ChromeDriver();

	@BeforeTest
	public void OpenWeb() {
		// Open WebSite
		driver.get("https://smartbuy-me.com/smartbuystore/en/");
		driver.manage().window().maximize();

	}

	@Test(priority = 1)
	public void Buyproduct() throws InterruptedException {
		Thread.sleep(2000);

		// Search process
		driver.findElement(By.xpath("//*[@id=\"js-site-search-input\"]")).sendKeys("ASUS Vivobook");//write of name product in search 
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("/html/body/main/header/div[4]/div/nav/div/div[2]/div/div/div/div/form/div/span/button/span"))
				.click();//click on search icon
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)", "");//scroll
		Thread.sleep(1000);
		// Select process
		driver.findElement(By.xpath("//*[@id=\"change_view\"]/div/div/div/ul/div[1]/div/div/a/img")).click();//select item 
		WebElement PriceLap = driver
				.findElement(By.xpath("/html/body/main/div[3]/div[1]/div[1]/div[3]/div[2]/div[2]/div[1]/div[1]/p"));//description for price item
		String Laptop = PriceLap.getText();
		String Pricewithoutjd = removeAfterSpace(Laptop);//
		double Laptopprice = Double.parseDouble(Pricewithoutjd);

		Thread.sleep(2000);
		// Add to the cart
		driver.findElement(By.xpath("//*[@id=\"addToCartButton\"]")).click();//click on add to cart button
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();//click on check out button
		Thread.sleep(1000);

		driver.findElement(By.xpath("/html/body/main/div[3]/div[1]/div[2]/div[5]/div/div/div[1]/button")).click();//click on check out button
		Thread.sleep(2000);
        // Login process
		driver.findElement(By.xpath("(//input[@id='j_username'])[2]")).sendKeys("hamzahalhumsi@hotmail.com");//fill email field
		driver.findElement(By.xpath("(//input[@id='j_password'])[2]")).sendKeys("hamzah123");//fill password field
		driver.findElement(By.xpath("(//div[contains(@class,'col-md-6')])[3]")).click();//click on Log In and Check Out button
		Thread.sleep(1000);
		//placing an order process
		driver.findElement(By.xpath("//*[@id=\"countrySelector\"]/div/div/div/button/span[1]")).click();//click on DDL Location to choose
        // (I can use Search at city) but I write this for loop to you know what can I write at automation test 
		for(int i=2;i<16;i++) {
			WebElement city= driver
					.findElement(By.xpath("//*[@id=\"countrySelector\"]/div/div/div/div/ul/li["+i+"]/a/span[1]"));
			String namectiy = city.getText();
			if(namectiy.contains("Amman"))
			{
				driver.findElement(By.xpath("//*[@id=\"countrySelector\"]/div/div/div/div/ul/li["+i+"]/a/span[1]")).click();
				break;
			}
		}
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//input[@id='us3-address'])[1]")).sendKeys("Tla Al-Ali");//fill Address field
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,900)", "");//scroll
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"i18nAddressForm\"]/div[10]/div/div/div/div/button")).click();
		driver.findElement(By.xpath("//*[@id=\"i18nAddressForm\"]/div[10]/div/div/div/div/div/div/input")).sendKeys("jo");//search for Jordan
		driver.findElement(By.xpath("//*[@id=\"i18nAddressForm\"]/div[10]/div/div/div/div/div/ul/li[114]/a")).click();//select Jordan
		driver.findElement(By.xpath("//*[@id=\"addressSubmit\"]")).click();//click next button to next step
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"deliveryMethodSubmit\"]")).click();//show how price the delivery and click next button to next step
		Thread.sleep(1000);
		WebElement Delivery = driver
				.findElement(By.xpath("/html/body/main/div[3]/div[1]/div/div[2]/div[2]/div/div/div/div[2]/span"));//description for delivery price
		String Deliverysmart = Delivery.getText();//description for delivery price get text(string)
		String Deliverywithoutjd = removeAfterSpace(Deliverysmart);//description for delivery price and remove after space to change to double
		double DeliveryPrice = Double.parseDouble(Deliverywithoutjd);//description for delivery price string to double
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"timeSlotForm\"]/div[1]/div[2]/div/label")).click();//select time slot
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/div/a[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[3]/td[4]/a")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"chooseDeliveryMethod_continue_button\"]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"paymentMethodForm\"]/div[1]/div/ul/li[2]/div[1]/label/img")).click();//choose the Payment Method
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"chooseDeliveryMethod_continue_button\"]")).click();
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,500)", "");
		driver.findElement(By.xpath("//*[@id=\"Terms1\"]")).click();//click to agree of Terms And Conditions
		Thread.sleep(1000);

		driver.findElement(By.xpath("//*[@id=\"silentOrderPostForm\"]/button")).click();//click next button to next step
		double ORDERTOTAL = DeliveryPrice + Laptopprice;
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,500)", "");
		WebElement total = driver.findElement(
				By.xpath("/html/body/main/div[3]/div[1]/div/div[1]/div[2]/div/div[2]/div/div/div/div/div[3]/span"));//description for total price
		String totalprice = total.getText();
		String totalwithoutjd = removeAfterSpace(totalprice);
		double TotalPrice = Double.parseDouble(totalwithoutjd);
		//assert the (Laptop price and delivery price ) with Total price
		Assert.assertEquals(TotalPrice, ORDERTOTAL);
		Thread.sleep(2000);
	}

	@AfterTest
	public void post() {
		driver.close();

	}
}

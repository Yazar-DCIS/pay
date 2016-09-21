package com.formsdirectinc.qa.app.payment;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.formsdirectinc.qa.TestBase;
import com.formsdirectinc.qa.enums.Products;
import com.formsdirectinc.qa.enums.Sites;
import com.formsdirectinc.qa.pages.Login;
import com.formsdirectinc.qa.tags.SelectElement;
import com.formsdirectinc.qa.utils.CaptureScreen;
import com.formsdirectinc.qa.utils.PropertyResource;

/**
 * PaymentPage: Selenium Page Model for Payment Page
 * 
 * @author: <a href="mailto:orina.moorthy@dcis.net">Orina</a>
 * @Date: 6/29/15
 * @Updated:24.02.2016
 */
public class PaymentTest extends TestBase {

	@BeforeTest
	public void startTest() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Test
	@Parameters({ "sitename", "siteurl", "product", "username", "password" })
	public void test(String sitename, String siteurl, String product,
			String username, String password) {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PropertyResource propertyFile = new PropertyResource();
		Properties data = propertyFile.loadProperty("payment");
		Sites site;
		if (System.getProperty("site.name") == null) {
			site = Sites.valueOf(sitename);
		} else {
			site = Sites.valueOf(System.getProperty("site.name"));
		}

		String myproduct = "%s";
		String testProduct = null;

		if (System.getProperty("product") == null) {

			testProduct = Products.valueOf(
					String.format(myproduct, product).toUpperCase())
					.getProductName();

		} else {

			testProduct = Products.valueOf(
					String.format(myproduct, System.getProperty("product"))
							.toUpperCase()).getProductName();

		}

		String paymentURL = "%s/" + data.getProperty("registerUrl")
				+ testProduct;

		if (siteURL() == null) {
			driver.get(String.format(paymentURL, siteurl));
		} else {
			driver.get(String.format(paymentURL, siteURL()));
		}

		SelectElement signInBtn = new SelectElement(driver,
				"//a[contains(text(),'Sign In')]");
		signInBtn.selectElementUsingXPath();

		String currentUrl = driver.getCurrentUrl();

		if (currentUrl.contains("login")) {
			Login signIn = new Login(getDriver());
			signIn.login(username, password, site);
		}

		Payment payForProduct = new Payment(driver);
		if (site.name().contains(Sites.FR.name())) {
			payForProduct.setFirstNameAsInCard(data
					.getProperty("CardFirstNameFR"));
		} else {

			payForProduct.setFirstNameAsInCard(data
					.getProperty("CardFirstName"));
			payForProduct.setLastNameAsInCard(data.getProperty("CardLastName"));
		}

		CaptureScreen myscreen = new CaptureScreen(driver);
		myscreen.takeScreenShot(product, "PaymentPage");

		if (site.name().contains(Sites.FR.name())) {
			payForProduct.selectCardType(data.getProperty("SelectCardType"));
		} else {
			payForProduct.setCardType(data.getProperty("CardType"));
		}
		payForProduct.setCardNumber(data.getProperty("CardNumber"));
		payForProduct.setCardMonth(data.getProperty("CardMonth"));
		payForProduct.setCardYear(data.getProperty("CardYear"));
		payForProduct.setCardPinNumber(data.getProperty("CardPIN"));

		payForProduct.setZipcode(data.getProperty("CardZipcode"));
		payForProduct.agreeCardTerms();

		if (site.name().contains(Sites.FR.name())
				|| site.name().contains(Sites.ID.name())
				|| site.name().contains(Sites.AIC.name())) {
			payForProduct.setCardState(data.getProperty("CardState"));
		}

		/* Select the submit payment---> */
		SelectElement selectPayment = new SelectElement(driver,
				data.getProperty("doPayment" + site));
		selectPayment.selectElementUsingXPath();

		/* Select the alert box---> */
		driver.switchTo().alert().accept();

		payForProduct.confirmPayment(product);
		setApplicationID(parseApplicationID());

		Logger logger = Logger.getLogger("ApplicationID");
		logger.info(getApplicationID());

		SelectElement startApp = new SelectElement(driver,
				"//a[contains(text(),'Sign Out')]");
		startApp.selectElementUsingXPath();
	}

	@AfterTest
	public void endTest() {
		driver.quit();
	}
}

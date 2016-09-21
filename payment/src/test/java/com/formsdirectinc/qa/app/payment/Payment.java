package com.formsdirectinc.qa.app.payment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.formsdirectinc.qa.tags.BaseTag;
import com.formsdirectinc.qa.utils.CaptureScreen;

/**
 * Sign UP: Selenium page Model for Register And Payment Controls
 * 
 * @author Orina
 * @Date: 24/02/2016
 */
public class Payment extends BaseTag {

	String cardNumber = "UMcard";
	String cardNumberDVD = "CARDNUM";
	String cardType = "cardType";

	String selectCard = "//span[contains(@class, '%s')]";

	String cardMonth = "month";
	String cardYear = "year";

	String cardPin = "UMcvv2";
	String cardPinDVD = "CIN";
	String cardFirstName = "firstname";

	String cardLastName = "lastname";
	String cardZipcode = "ZIP";

	String cardAgreeTerms = "termsagree";
	String cardStateBasedTerms = "stateBased";

	String cardState = "STATE";
	String cardShipingAddr = "isSameAsShippingAddress";

	WebElement valuefield, confirmvaluefield;

	String paymentSuccessExpression = "a[href='/applicationcenter.do']";
	WebElement paymentSuccessField;

	public Payment(WebDriver driver, String property) {
		super(driver, property);

	}

	public Payment(WebDriver driver) {
		super(driver);

	}

	public Payment setCardNumber(String answer) {
		valuefield = driver.findElement(By.id(cardNumber));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardNumberDVD(String answer) {
		valuefield = driver.findElement(By.id(cardNumberDVD));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardType(String answer) {
		valuefield = driver.findElement(By.id(cardType));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment selectCardType(String answer) {
		valuefield = driver.findElement(By.xpath(String.format(selectCard,
				answer)));
		valuefield.click();

		return this;
	}

	public Payment setCardMonth(String answer) {
		valuefield = driver.findElement(By.id(cardMonth));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardYear(String answer) {
		valuefield = driver.findElement(By.id(cardYear));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardMonthName(String answer) {
		valuefield = driver.findElement(By.name(cardMonth));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardYearName(String answer) {
		valuefield = driver.findElement(By.name(cardYear));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardPinNumber(String answer) {
		valuefield = driver.findElement(By.id(cardPin));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardPinNumberDVD(String answer) {
		valuefield = driver.findElement(By.id(cardPinDVD));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setFirstNameAsInCard(String answer) {
		valuefield = driver.findElement(By.id(cardFirstName));
		valuefield.clear();
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setLastNameAsInCard(String answer) {
		valuefield = driver.findElement(By.id(cardLastName));
		valuefield.clear();
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setZipcode(String answer) {
		valuefield = driver.findElement(By.id(cardZipcode));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment setCardState(String answer) {
		valuefield = driver.findElement(By.id(cardState));
		valuefield.sendKeys(answer);

		return this;
	}

	public Payment agreeCardTerms() {
		valuefield = driver.findElement(By.name(cardAgreeTerms));
		valuefield.click();

		return this;
	}

	public Payment agreeCardStateTerms() {
		valuefield = driver.findElement(By.id(cardStateBasedTerms));
		valuefield.click();

		return this;
	}

	public Payment setSameShippingAddress() {
		valuefield = driver.findElement(By.id(cardShipingAddr));
		valuefield.click();

		return this;
	}

	public Payment confirmPayment(String productname) {

		paymentSuccessField = driver.findElement(By
				.cssSelector(paymentSuccessExpression));

		CaptureScreen myscreen = new CaptureScreen(driver);
		myscreen.takeScreenShot(productname, "PaymentSuccessPage");

		paymentSuccessField.click();

		return this;
	}

}

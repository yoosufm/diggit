package com.diggit.qa.common;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumBase {

	public static WebDriver DRIVER;
	

	public SeleniumBase(){
		DRIVER = DriverFactory.getWebDriver("Chrome");
		DRIVER.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		DRIVER.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		DRIVER.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public SeleniumBase(String url){
		DRIVER = DriverFactory.getWebDriver("Firefox");
		DRIVER.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		DRIVER.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		DRIVER.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		navigateTo(url);
	}

	public void type(By by, String text){

		((JavascriptExecutor)DRIVER).executeScript("window.scrollTo(0," + DRIVER.findElement(by).getLocation().y + ")");
		
		DRIVER.findElement(by).clear();
		
		DRIVER.findElement(by).sendKeys(text);
	}

	public void type(By by, String text, String text1){

		DRIVER.findElement(by).clear();

		DRIVER.findElement(by).sendKeys(text);	

	}

	public void click(By by){

		DRIVER.findElement(by).click();
	}
	
	public void navigateTo(String url){

		DRIVER.navigate().to(url);
	}

	public void check(By by){
		if(!DRIVER.findElement(by).isSelected()){
		
			DRIVER.findElement(by).click();
		}

	}

	public void clickAndWaitForPageToLoad(By by){

		DRIVER.findElement(by).click();
		
	}

	public void selectByIndex(By by, int index){

		new Select(DRIVER.findElement(by)).selectByIndex(index);
	}

	public void selectByValue(By by, String value){

		if(!value.equals(""))
			new Select(DRIVER.findElement(by)).selectByValue(value);

	}

	public void selectByVisibleText(By by, String text){

		if(!text.equals(""))
			new Select(DRIVER.findElement(by)).selectByVisibleText(text);

	}

	public void selectByVisibleTextAndWait(By by, String text){
		if(!text.equals(""))
			new Select(DRIVER.findElement(by)).selectByVisibleText(text);		

	}

	public void selectFromCustomizedDropdown(By dropDownLocator, By searchTextBox, By options, String label)
	{		
		
		if(label.length() !=0 ){			
			
			click(dropDownLocator);			
			
			type(searchTextBox, label);			
			
			List<WebElement> elements = DRIVER.findElements(options);
			
			WebElement element;
			
			System.out.println(" 888888888888888888888888  ------------------------------  " + elements.size());

			for(int index = 0; index< elements.size(); index++){
				System.out.println(index + " ------------------------------  " + elements.get(index).getText());
				if( elements.get(index).getText().contains(label)){
					
					elements.get(index).click();
					break;				
				}				
			}
		}
	}
	
	public void selectFromCustomizedDropdownWithoutTyping(By dropDownLocator, By searchTextBox, By options, String label)
	{		
		
		if(label.length() !=0 ){			
			
			click(dropDownLocator);	
			
			//type(searchTextBox, label);	
			
			List<WebElement> elements = DRIVER.findElements(options);
			
			WebElement element;

			for(int index = 0; index< elements.size(); index++){
				
				if( elements.get(index).getText().contains(label)){
					
					elements.get(index).click();
					break;				
				}				
			}
		}
	}

	public String getUrl(){

		return DRIVER.getCurrentUrl();
	}

	public String getText(By by){

		return DRIVER.findElement(by).getText();

	}

    public String getText(WebElement element){

        return element.getText();

    }

	public String getSelectedValue(By by){

		return new Select(DRIVER.findElement(by)).getFirstSelectedOption().getText();
	}

	public String getValue(By by){
		return DRIVER.findElement(by).getAttribute("value");
	}

	public boolean isChecked(By by){
		return DRIVER.findElement(by).isSelected();
	}

	public WebElement getElement(By by){
		return DRIVER.findElement(by);
	}


	public List<WebElement> getElements(By by){
		return DRIVER.findElements(by);
	}

	public void pageRefresh(){
		DRIVER.navigate().refresh();
	}

	public boolean isElementPresent(By by){
		try{
		return DRIVER.findElement(by).isDisplayed();
		}catch(Exception e){
			return false;
		}
	}

	public void keyboardEnter(By by){
		DRIVER.findElement(by).sendKeys(Keys.ENTER);
	}

	public void watitForAjax(){
		(new WebDriverWait(DRIVER, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				return (Boolean) js.executeScript("return jQuery.active == 0");
			}
		});
	}
	
	public String acceptAllert() {

		String text = "";

		try {

			// Check the presence of alert
			Alert alert = DRIVER.switchTo().alert();
			// Alert present; set the flag
			text = alert.getText();
			// if present consume the alert
			alert.accept();
			
		} catch (NoAlertPresentException ex) {
			// Alert not present
			ex.printStackTrace();
		}

		return text;

	}
	
	public void waitForOverlayVanished(){
		try{
		waitForEnabled(By.cssSelector("div.blockUI.blockOverlay"), 15);
		waitForElementNoToPresent(By.cssSelector("div.blockUI.blockOverlay"), 5);
		}catch(Exception e){
			
		}
	}
	
	public String dismissAllert() {

		String text = "";

		try {

			// Check the presence of alert
			Alert alert = DRIVER.switchTo().alert();
			// Alert present; set the flag
			text = alert.getText();
			// if present consume the alert
			alert.dismiss();
			
		} catch (NoAlertPresentException ex) {
			// Alert not present
			ex.printStackTrace();
		}

		return text;

	}
	
	public boolean isElementEnabled(By by){
		
		//WebDriverWait wait = new WebDriverWait(DRIVER, 15);
		//wait.until(DRIVER.findElement(by).isEnabled());
		
		return DRIVER.findElement(by).isEnabled();
	}

	public String getPageTitle(){
		return DRIVER.getTitle();
	}
	public WebElement getDesiredRow(String quoteId, WebElement table) throws InterruptedException{

		WebElement tableBody = table.findElement(By.tagName("tbody"));		
		List <WebElement> rows = tableBody.findElements(By.tagName("tr"));		
		boolean isRecordAvailable = false;
		WebElement row = null;

		for(int rowIndex = 0; rowIndex < 10 ; rowIndex ++){
			List <WebElement> columns = rows.get(rowIndex).findElements(By.tagName("td"));
			if(columns.get(1).getText().equalsIgnoreCase(quoteId)){
				isRecordAvailable = true;
				row =  rows.get(rowIndex);
				break;
			}
		}

		if(isRecordAvailable == false){
			for(;tableBody.findElement(By.linkText("Next >")).isDisplayed();){
				tableBody.findElement(By.linkText("Next >")).click();				

				table = DRIVER.findElement(By.xpath("//table[@class='no_sortable browse']"));
				tableBody = table.findElement(By.tagName("tbody"));				
				rows = tableBody.findElements(By.tagName("tr"));
				for(int rowIndex = 0; rowIndex < 10 ; rowIndex ++){

					List <WebElement> columns = rows.get(rowIndex).findElements(By.tagName("td"));

					if(columns.get(1).getText().equalsIgnoreCase(quoteId)){						

						isRecordAvailable = true;
						row =  rows.get(rowIndex);
						break;
					}					
				}				
				if (isRecordAvailable) break;				
			}
		}

		return row;
	}
	
	 public void selectWindow(String title)
     {
         Set<String> handles = DRIVER.getWindowHandles();

         Iterator ite=handles.iterator();

         while(ite.hasNext())
         {
             String popupHandle=ite.next().toString();
             if(!popupHandle.contains(title))
             {
            	 DRIVER.switchTo().window(popupHandle);                        
             }
         }
     }
	 
	 public void waitForEnabled(By by, int timeOut){
		 WebDriverWait wait = new WebDriverWait(DRIVER, timeOut);
		 WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
	 }

	 public void waitForElementPresent(By by, int timeOut){
		 WebDriverWait wait = new WebDriverWait(DRIVER, timeOut);
		 WebElement element = wait.until(ExpectedConditions.visibilityOf(DRIVER.findElement(by)));
	 }
	 
	 public void waitForElementNoToPresent(By by, int timeOut){
		 WebDriverWait wait = new WebDriverWait(DRIVER, timeOut);
		 Boolean element = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	 }


}

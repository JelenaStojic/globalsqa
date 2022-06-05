package domaci;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomerList extends BasePage{

    private By search = By.xpath("/html/body/div/div/div[2]/div/div[2]/div/form/div/div/input");
    private By searchNameResults = By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody/tr/td[1]");
    private By accountNumber = By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody/tr/td[4]/span");

    public CustomerList(WebDriver driver, WebDriverWait driverWait) {
        super(driver, driverWait);
    }

    public void inputCustomerName(String addFirstName){
        getDriver().findElement(search).sendKeys(addFirstName);
    }

    public boolean doesCustomerExist(){
        return getDriver().findElement(searchNameResults).isDisplayed();
    }

    public boolean doesAccountNumberExist(){
        return getDriver().findElement(accountNumber).isDisplayed();
    }
}
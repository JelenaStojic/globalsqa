package domaci;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class RequestTest {
    private WebDriver driver;
    private WebDriverWait driverWait;
    private LoginPage loginPage;
    private Manager manager;
    private AddCustomer addCustomer;
    private OpenAccount openAccount;
    private Customer customer;
    private Account account;
    private CustomerList customersList;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ThinkPad\\IdeaProjects\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        loginPage = new LoginPage(driver, driverWait);
        manager = new Manager(driver, driverWait);
        addCustomer = new AddCustomer(driver, driverWait);
        openAccount = new OpenAccount(driver, driverWait);
        customer = new Customer(driver, driverWait);
        account = new Account(driver, driverWait);
        customersList = new CustomerList(driver, driverWait);
        driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
    }
    @AfterClass
    public void afterClass() {
        driver.close();
    }

    @Test(priority = 1)
    public void testBankManagerLogin() {
        loginPage.clickBankManagerLoginButton();
        Assert.assertTrue(manager.isVisibleAddCustomerButton());
    }

    @Test(priority = 2)
    public void testAddCustomer() {
        manager.clickAddCustomerButton();
        Assert.assertTrue(addCustomer.isVisibleFirstNameField());
        addCustomer.inputAddCustomerFirstName("Jelena");
        addCustomer.inputAddCustomerLastName("Stojic");
        addCustomer.inputAddCustomerPostCode("21000");
        addCustomer.clickCustomerSubmitButton();
        addCustomer.skipAlert();
        manager.clickCustomersListButton();
        customersList.inputCustomerName("Jelena");
        Assert.assertTrue(customersList.doesCustomerExist());
    }

    @Test(priority = 3)
    public void testOpenAccount() {
        manager.clickOpenAccountButton();
        openAccount.chooseCustomer(6);
        openAccount.chooseCurrency("Dollar");
        openAccount.clickProcessButton();
        openAccount.skipAlert();
        manager.clickCustomersListButton();
        customersList.inputCustomerName("Jelena");
        Assert.assertTrue(customersList.doesAccountNumberExist());
        manager.clickHomeButton();
    }

    @Test(priority = 4)
    public void testLogoutBankManager() {
        manager.clickLogoutBankManager();
        Assert.assertTrue(loginPage.isVisibleBankManagerLoginButton());
        manager.clickHomeButton();
    }


    @Test(priority = 5)
    public void testCustomerLogin() {
        loginPage.clickCustomerLoginButton();
        Assert.assertTrue(customer.isVisibleYourNameField());
        customer.chooseYourName(6);
        customer.clickLogin();
        Assert.assertTrue(account.isVisibleTransactionsButton());
    }

    @Test(priority = 6)
    public void testDepositForCustomer() {
        account.clickDeposit();
        Assert.assertTrue(account.isVisibleAmountToBeDeposited());
        account.chooseAmountToBeDeposited(5000);
        account.clickDepositButton();
        Assert.assertTrue(account.isVisibleDepositSuccessful());
    }

    @Test(priority = 7)
    public void testWithdrawalForCustomer() {
        account.clickWithdrawal();
        Assert.assertTrue(account.isVisibleWithdrawnToBeDeposited());
        account.chooseAmountToBeWithdrawn(1000);
        account.clickWithdrawButton();
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Assert.assertTrue(account.isVisibleTransactionSuccessful());
    }

    @Test(priority = 8)
    public void testLogoutCustomer() {
        account.clickLogoutCustomer();
        Assert.assertTrue(customer.isVisibleYourNameField());
    }
}

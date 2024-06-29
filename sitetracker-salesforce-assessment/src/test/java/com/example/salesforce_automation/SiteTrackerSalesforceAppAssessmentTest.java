package com.example.salesforce_automation;

import com.example.pages.LoginPage;
import com.example.pages.LeadsPage;
import com.example.pages.TasksPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class SiteTrackerSalesforceAppAssessmentTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String URL = "https://sitetracker-1a-dev-ed.develop.my.salesforce.com/";
    private static final String USERNAME = "qa-auto@sitetracker.com";
    private static final String PASSWORD = "Test123$";
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private LoginPage loginPage;
    private LeadsPage leadsPage;
    private TasksPage taskPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/pravalikamagham/Desktop/Selenium/drivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security", "--allow-running-insecure-content", "--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(URL);

        loginPage = new LoginPage(driver);
        leadsPage = new LeadsPage(driver, wait);
        taskPage = new TasksPage(driver, wait);
    }

    @Test(priority = 1)
    public void loginToSalesforce() {
        loginPage.login(USERNAME, PASSWORD);
        assert loginPage.isHomePageDisplayed() : "Login failed";
    }

    @Test(priority = 2, dependsOnMethods = "loginToSalesforce")
    public void navigateAndInteract() {
        leadsPage.navigateToLeads();
        assert leadsPage.isMyLeadsViewDisplayed() : "Not on My Leads view";
        leadsPage.applyDateFilter("01/01/2024", "06/25/2024");
        assert leadsPage.getLeadsCount() == 22 : "Incorrect leads count";
    }
    
    //while creating 'Submit Budget Plan for Review' task,set the date to 8 days from today as I need to fulfill the test 5 activityTabAndTaskValidation 
    @Test(priority = 3, dependsOnMethods = "navigateAndInteract")
    public void leadInteractionAndTaskCreation() {
        taskPage.openLead("Betty Bair");
        taskPage.createTask("Create Budget Plan", LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)), "In Progress");
        taskPage.createTask("Submit Budget Plan for Review", LocalDate.now().plusDays(8).format(DateTimeFormatter.ofPattern(DATE_FORMAT)), "Not Started");
//      taskPage.createTask("Submit Budget Plan for Review", LocalDate.now().plusWeeks(1).format(DateTimeFormatter.ofPattern(DATE_FORMAT)), "Not Started");
    }

    @Test(priority = 4, dependsOnMethods = "leadInteractionAndTaskCreation")
    public void activityTabAndTaskValidation() {
        assert taskPage.isTaskDisplayed("Create Budget Plan") : "Task 'Create Budget Plan' not displayed";
        assert taskPage.isTaskDisplayed("Submit Budget Plan for Review") : "Task 'Submit Budget Plan for Review' not displayed";
        taskPage.updateTaskDescription("Create Budget Plan", "Budget for Q4");
    }

    @Test(priority = 5, dependsOnMethods = "activityTabAndTaskValidation")
    public void filterAndDisplayAdjustments() {
        taskPage.setFilterAndValidateTasks("Create Budget Plan", "Submit Budget Plan for Review");
        taskPage.showAllActivitiesAndValidateTasks("Create Budget Plan", "Submit Budget Plan for Review");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

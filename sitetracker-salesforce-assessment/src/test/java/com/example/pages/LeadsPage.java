package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeadsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LeadsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void navigateToLeads() { 
        driver.findElement(By.xpath("//*[@class='slds-icon-waffle']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='slds-input']"))).sendKeys("Leads");
        driver.findElement(By.xpath("//*[@class='slds-truncate']/b[contains(text(),'Leads')]")).click();
    }

    public boolean isMyLeadsViewDisplayed() {
        WebElement myLeadsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'triggerLinkText selectedListView')]")));
        return myLeadsTab.isDisplayed();
    }

    public void applyDateFilter(String startDateStr, String endDateStr) {
        driver.findElement(By.xpath("//button[@title='Show filters']")).click();
        driver.findElement(By.xpath("(//a[@class='trigger' and @href='javascript:void(0);'])[1]")).click();
        driver.findElement(By.xpath("(//input[@class='slds-input' and @required=''])[1]")).clear();
        driver.findElement(By.xpath("(//input[@class='slds-input' and @required=''])[1]")).sendKeys(startDateStr);
        driver.findElement(By.xpath("(//input[@class='slds-input' and @required=''])[2]")).clear();
        driver.findElement(By.xpath("(//input[@class='slds-input' and @required=''])[2]")).sendKeys(endDateStr);
        driver.findElement(By.xpath("//span[contains(text(),'Done')]")).click();
    }

    public int getLeadsCount() {
        WebElement leadsCountElement = driver.findElement(By.xpath("//p[@class='slds-text-align_left kpiDisplayText']"));
        return Integer.parseInt(leadsCountElement.getText());
    }
}

package com.example.pages;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class TasksPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public TasksPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openLead(String leadName) {
        WebElement lead = driver.findElement(By.xpath("//a[@title='" + leadName + "']"));
        lead.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Ms " + leadName + "')]")));
    }

    public void createTask(String taskName, String dueDate, String status) {
        navigateToActivityTab();
        clickNewTaskButton();

        WebElement subjectField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='slds-combobox__input slds-input']")));
        subjectField.sendKeys(taskName);

        WebElement dueDateField = driver.findElement(By.xpath("//input[@class='slds-input']"));
        dueDateField.sendKeys(dueDate);

        selectStatus(status);

        WebElement saveButton = driver.findElement(By.xpath("//button[contains(@class, 'slds-button') and contains(@class, 'slds-button--brand') and contains(@class, 'uiButton')]//span[text()='Save']"));
        saveButton.click();

        validateTaskCreation(taskName);
    }

    public void navigateToActivityTab() {
        WebElement activityTab = driver.findElement(By.xpath("//a[@data-label='Activity']"));
        activityTab.click();
    }

    public void clickNewTaskButton() {
        WebElement newTaskButton = driver.findElement(By.xpath("//span[@value='NewTask']"));
        newTaskButton.click();
    }

    public void selectStatus(String status) {
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@role='combobox' and contains(@class, 'select')]")));
        statusDropdown.click();

        WebElement statusOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'select-options')]//a[@title='" + status + "']")));
        statusOption.click();
    }

    public void validateTaskCreation(String taskName) {
        WebElement taskNotification = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(@class, 'toastMessage') and contains(@class, 'slds-text-heading--small') and contains(@class, 'forceActionsText') and contains(., 'Task') and contains(., '" + taskName + "') and contains(., 'was created')]")));
        Assert.assertEquals(taskNotification.getText(), "Task \"" + taskName + "\" was created.");
        //System.out.println(taskNotification.getText());
    }

    public boolean isTaskDisplayed(String taskName) {
        try {
            WebElement task = driver.findElement(By.xpath("//a[@title='" + taskName + "']"));
            return task.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void updateTaskDescription(String taskName, String description) {
        WebElement task = driver.findElement(By.xpath("//a[text()='" + taskName + "']"));
        WebElement taskExpand = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//lightning-icon[@icon-name='utility:chevronright' and @title='Details for " + taskName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", taskExpand);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", taskExpand);

        WebElement moreActionsIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='" + taskName + "']/ancestor::div[contains(@class, 'timelineItem')]//div[contains(@class, 'slds-timeline__actions')]//a[contains(@class, 'rowActionsPlaceHolder')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", moreActionsIcon);

        WebElement editComments = driver.findElement(By.xpath("//a[@title='Edit Comments']"));
        editComments.click();

        WebElement descriptionField = driver.findElement(By.xpath("//textarea[@role='textbox']"));
        descriptionField.clear();
        descriptionField.sendKeys(description);

        WebElement saveDescriptionButton = driver.findElement(By.xpath("//div[@class=\"modal-footer slds-modal__footer\"]//button/span[contains(text(),'Save')]"));
        saveDescriptionButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"modal-footer slds-modal__footer\"]//button/span[contains(text(),'Save')]")));       
        WebElement taskIcon=  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//lightning-icon[@icon-name='utility:chevronright' and @title='Details for Create Budget Plan'])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", taskIcon);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", taskIcon);
        WebElement updatedDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'slds-size_2-of-2')]//div[@class='slds-item--detail description bodyText tenLinesScroll' and contains(text(), 'Budget for Q4')]")));
        Assert.assertTrue(updatedDescription.isDisplayed(), "Description update failed");
        System.out.println("Updated description displayed as : " + updatedDescription.getText());       
    }
    
    public void setFilterAndValidateTasks(String displayedTask, String hiddenTask) {
        WebElement gearIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Timeline Settings']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", gearIcon);

        WebElement next7DaysRadioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next 7 days']/preceding-sibling::span[@class='radioFaux slds-radio_faux slds-var-m-right_x-small']")));
        next7DaysRadioButton.click();
        
        WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'Apply')])[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyButton);
        //applyButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//button[contains(text(),'Apply')])[2]")));       
        validateTaskVisibility(displayedTask, true);
        validateTaskVisibility(hiddenTask, false);
    }

    public void showAllActivitiesAndValidateTasks(String... taskNames) {
        WebElement showAllActivities = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Show All Activities']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showAllActivities);
        
        //showAllActivities.click();

        for (String taskName : taskNames) {
            WebElement task = driver.findElement(By.xpath("//a[text()='" + taskName + "']"));
            Assert.assertTrue(task.isDisplayed(), "Task " + taskName + " is not displayed after showing all activities");
        }
    }

    private void validateTaskVisibility(String taskName, boolean shouldBeVisible) {
        try {
            WebElement task = driver.findElement(By.xpath("//a[@title='" + taskName + "']"));
            Assert.assertEquals(task.isDisplayed(), shouldBeVisible, "Task " + taskName + " visibility mismatch");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            Assert.assertFalse(shouldBeVisible, "Task " + taskName + " should not be visible");
        }
    }
}

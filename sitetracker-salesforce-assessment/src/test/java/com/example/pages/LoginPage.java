package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("Login")).click();
    }

    public boolean isHomePageDisplayed() {
        return driver.findElement(By.xpath("(//span[@class='title slds-truncate' and text()='Home'])[1]")).isDisplayed();
    }
}

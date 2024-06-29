# Salesforce Automation Assessment

## Overview
This project contains Selenium WebDriver automation scripts for automating Salesforce interactions using Java and TestNG.

## Prerequisites
- Java Development Kit (JDK) 8 or later
- Eclipse IDE
- Selenium Java and TestNG libraries
- ChromeDriver executable

## Setup Instructions

1. **Clone the repository**:
    ```sh
    git clone https://github.com/pravalikamadisetti/SiteTrackerAssessment.git
    ```

2. **Open the project in Eclipse**:
    - Open Eclipse IDE
    - Select File > Import > Existing Projects into Workspace
    - Select the cloned repository folder

3. **Add required libraries**:
Created a Maven project and added the dependencies for the required libraries otherwise can do the below steps mentioned.
    - Right-click on the project > Build Path > Configure Build Path
    - Click on Libraries tab > Add External JARs
    - Add the Selenium Java and TestNG JAR files

4. **Update `chromedriver` path**:
    - In `SiteTrackerSalesforceAppAssessmentTest.java`, update the path to the `chromedriver` executable.

5. **Run the tests**:
    - Refer to the package com.example.salesforce_automation (under src/test/java),right-click on `SiteTrackerSalesforceAppAssessmentTest` class > Run As > TestNG Test
    - Project folder structure
      src -

## Execution Instructions
- Ensure that the ChromeDriver version matches the installed Google Chrome browser version.
- Ensure the Salesforce sandbox URL and credentials are correctly configured.
- Adjust any XPath or element locators if there are changes in the Salesforce UI.

 

Notes:
* Ensure the chromedriver path in your script is correctly pointing to the location of the chromedriver executable on your machine.
* Adjust the XPath locators according to the actual elements in your Salesforce sandbox as they might differ.

Test Scenarios
The automated test scenarios include:
1. Login to Salesforce
    * Opens the Salesforce login page and logs in using provided credentials.
2. Navigate and Interact with Leads
    * Navigates to the Leads section.
    * Validates the "My Leads" view and applies date filters.
3. Lead Interaction and Task Creation
    * Opens a specific lead and creates tasks with different due dates and statuses.
4. Activity Tab and Task Validation
    * Validates tasks under the Activities tab and updates task descriptions.
5. Filter and Display Adjustments
    * Applies filters to display tasks for the next 7 days and validates task visibility.
    * Shows all activities and validates task visibility.


Page Object Model - Created the following page classes
LoginPage.java
Handles interactions with the Salesforce login page.
LeadsPage.java
Handles interactions with the Leads page, including navigation and validation of leads.
TaskPage.java
Handles interactions with tasks, including creation, validation, and updates.



 

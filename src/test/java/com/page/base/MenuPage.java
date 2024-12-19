package com.page.base;

import com.data.dynamic.FakerDataFactory;

import java.nio.file.Paths;

public class MenuPage extends CognitestPageHook {
    public FakerDataFactory dataFactory;
    // Locator for username field
    protected String usernameField = "//input[@name = 'username']";

    // Locator for password field
    protected String passwordField = "//input[@name = 'password']";

    // Locator for login button
    protected String loginButton = "//button[@type= 'submit']";

    protected String firstName = "//input[@name = 'firstName']";

    protected String middleName = "//input[@name = 'middleName']";

    protected String lastName = "//input[@name = 'lastName']";

    protected String paygradeFieldLocator = "(//input[@class = 'oxd-input oxd-input--active'])[2]";

    protected String jobtitleFieldLocator = "(//input[@class='oxd-input oxd-input--active'])[2]";


    public MenuPage navigateToLoginPage() {
        getPage().navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        return this;
    }

    public MenuPage enterUsername(String username) {
        dataFactory = new FakerDataFactory();
        typeWithType("//input[@name = 'username']", "Admin", "Username");
        return this;
    }

    public MenuPage enterPassword(String password) {
        typeWithType("//input[@name = 'password']", "admin123", "Password");
        return this;
    }

    public MenuPage clickOnLoginButton() {
        click("//button[@type= 'submit']", "Login");
        return this;
    }

    public MenuPage navigateToPIMModule() {
        // Assuming the locator for PIM module navigation
        String pimModuleLocator = "//span[.='PIM']";
        click(pimModuleLocator, "PIM Module");
        return this;
    }

    // Navigation of admin module
    public MenuPage navigateToAdminModule() {
        String adminModuleLocator = "(//span[.='Admin'])[1]";
        click(adminModuleLocator, "Admin Module");
        return this;
    }

    public MenuPage clickOnJobTitleButton() {
        String jobTitleButtonLocator = "(//span[@class ='oxd-topbar-body-nav-tab-item'])[2]";
        click(jobTitleButtonLocator, "job options");
        return this;
    }

    public MenuPage clickOnJobTitle() {
        String jobTitleLocator = "//a[.='Job Titles']";
        click(jobTitleLocator, "job title Button");
        return this;
    }

    public MenuPage clickaddOnJobTitle() {
        String addJobTitleLocator = "(//button[@type = 'button'])[4]";
        click(addJobTitleLocator, "add job title Button");
        return this;
    }

    public MenuPage enterJobTitle(String jobtitle) {
        typeWithType("(//input[@class='oxd-input oxd-input--active'])[2]", "Automation tester - Playwright, Java", "jobtitle");
        return this;
    }

    public MenuPage enterJobDescription() {
        type("//textarea[@placeholder = 'Type description here']", FakerDataFactory.getDescription(), "job description");
        return this;

    }

    public MenuPage jobTitlecreation() {
        String jobTitlesaveLocator = "//button[@type = 'submit']";
        click(jobTitlesaveLocator, "job title save Button");
        return this;
    }

    public MenuPage editjobtitle() {
        String editJobtitleLocator = "(//button[@type = 'button'])[6]";
        click(editJobtitleLocator, "job title edit Button");
        return this;
    }

    public MenuPage deletionjobtitle() {
        String deletionJobtitleLocator = "(//button[@type = 'button'])[5]";
        click(deletionJobtitleLocator, "job title delete Button");
        return this;
    }

    public MenuPage confirmDeletion() {
        String deletionconfirmationLocator = "//button[@class ='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']";
        click(deletionconfirmationLocator, "Yes,Delete button");
        return this;
    }

    public MenuPage clickPaygrades() {
        String payGradeLocator = "//a[.='Pay Grades']";
        click(payGradeLocator, "pay Grade Button");
        return this;
    }

    public MenuPage addPaygrades() {
        String addpayGradeLocator = "(//button[@type = 'button'])[4]";
        click(addpayGradeLocator, "pay Grade Button");
        return this;
    }

    public MenuPage enterPayGradeName(String payGradeName) {
        typeWithType("(//input[@class = 'oxd-input oxd-input--active'])[2]", "c+", "PayGrade");
        return this;
    }

    public MenuPage savePayGradeName() {
        String savepayGradeLocator = "//button[@type = 'submit']";
        click(savepayGradeLocator, "save pay Grade Button");
        return this;
    }

    public MenuPage editpaygrade() {
        String editjobPaygradeLocator = "(//button[@type = 'button'])[6]";
        click(editjobPaygradeLocator, "pay grade edit Button");
        return this;
    }

    public MenuPage deletionjobpaygrade() {
        String deletionJobpaygradeLocator = "(//button[@type = 'button'])[5]";
        click(deletionJobpaygradeLocator, "job title delete Button");
        return this;
    }

    public MenuPage confirmDeletionpaygrade() {
        String deletionconfirmationpaygradeLocator = "//button[@class ='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']";
        click(deletionconfirmationpaygradeLocator, "Yes,Delete button");
        return this;
    }

    public MenuPage clickOnAddEmployeeButton() {
        String addEmployeeButtonLocator = "//a[.='Add Employee']";
        click(addEmployeeButtonLocator, "Add Employee Button");
        return this;
    }


    public MenuPage enterFirstName() {
        type("//input[@name = 'firstName']", FakerDataFactory.getFirstName(), "First Name");
        return this;
    }

    public MenuPage enterMiddleName() {
        type("//input[@name = 'middleName']", FakerDataFactory.getMiddleName(), "Middle Name");
        return this;
    }

    public MenuPage enterLastName() {
        type("//input[@name = 'lastName']", FakerDataFactory.getLastName(), "Last Name");
        return this;
    }

    public MenuPage clickOnSaveButton() {
        String saveButtonLocator = "//button[@type ='submit']";
        click(saveButtonLocator, "Save Button");
        return this;
    }

    public MenuPage employeeName() {
        type("//input[@name = 'middleName']", FakerDataFactory.getFirstName(), "Middle Name");
        return this;
    }

    public MenuPage navigateToAddEmployeePage() {
        String addEmployeePageLocator = "//a[.='Employee List']"; // Adjust the locator based on actual implementation
        click(addEmployeePageLocator, "Employee List Button");
        return this;
    }

    public MenuPage employee() {
        type("(//input[@placeholder = 'Type for hints...'])[1]", FakerDataFactory.getFirstName(), "First Name");
        return this;
    }

    public MenuPage clickOnSearch() {
        click("//button[@type = 'submit']", "Search Button");
        return this;
    }

    public MenuPage organizationButton() {
        String organizationLocator = "(//span[@class ='oxd-topbar-body-nav-tab-item'])[3]";
        click(organizationLocator, "job options");
        return this;
    }

    public MenuPage clickOnGeneralinformation() {
        String generalinformationLocator = "//a[.='General Information']";
        click(generalinformationLocator, "general information Button");
        return this;
    }

    public MenuPage editToggle() {
        String editLocator = "//span[@class= 'oxd-switch-input oxd-switch-input--active --label-left']";
        click(editLocator, "edit toggle");
        return this;
    }

    public MenuPage enterOrganizationName(String organizationName) {
        typeWithType("(//input[@class = 'oxd-input oxd-input--active'])[2]", "tickingminds software limited", "organization Name");
        return this;
    }

    public MenuPage updatingGeneralinformation() {
        String GeneralinformationsaveLocator = "//button[@type = 'submit']";
        click(GeneralinformationsaveLocator, "save Button");
        return this;
    }

    public MenuPage Employeestatusbutton() {
        String employeestatusLocator = "//a[.='Employment Status']";
        click(employeestatusLocator, "Employee status Button");
        return this;
    }

    public MenuPage addEmployeestatus() {
        String addemployeestatusLocator = "(//button[@type = 'button'])[4]";
        click(addemployeestatusLocator, "Add Employee status Button");
        return this;
    }

    public MenuPage enterEmployeeName(String employeeName) {
        typeWithType("(//input[@class = 'oxd-input oxd-input--active'])[2]", "user3210", "Name");
        return this;
    }

    public MenuPage clickOnEmployeeListButton() {
        String employeeListButton = "//button[@class = 'oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']"; // Locator for employee list button
        click(employeeListButton, "Employee List Button");
        return this;
    }

    public MenuPage clickEditButton() {
        String editButton = "(//button[@type = 'button'])[6]"; // Locator for employee list button
        click(editButton, "Edit Button");
        return this;
    }

    public MenuPage changeEmployeeName(String newEmployeeName) {
        type("(//input[@class = 'oxd-input oxd-input--active'])[2]", "testusers123", "Name");
        return this;
    }
    public MenuPage clickOnDeleteButton() {
        String deleteButtonLocator = "(//button[@type = 'button'])[5]";
        click(deleteButtonLocator, "Delete Button");
        return this;
    }

    public MenuPage employeestatusconfirmDeletion() {
        String confirmButtonLocator = "//button[@class ='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']";
        click(confirmButtonLocator, "Confirm Deletion Button");
        return this;
    }
    public MenuPage jobcategoryOption() {
        String jobCategoryoption = "//a[.='Job Categories']"; // Locator for Save Button
        click(jobCategoryoption , "jobCategory Button");
        return this;
    }
    public MenuPage addjobcategoryButton() {
        String addjobcategorButton = "(//button[@type = 'button'])[4]"; // Locator for Save Button
        click(addjobcategorButton , "jobCategory Button");
        return this;
    }

    public MenuPage enterValidJobCategory(String jobCategory) {
        String jobCategoryField = "(//input[@class = 'oxd-input oxd-input--active'])[2]"; // Locator for Job Category input field
        type("(//input[@class = 'oxd-input oxd-input--active'])[2]","Testing - Java", "Job Category");
        return this;
    }

    public MenuPage addJobCategory() {
        String saveButton = "//button[@class = 'oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']"; // Locator for Save Button
        click(saveButton, "Save Button");
        return this;
    }
    public MenuPage clickonusermanagement() {
        String userManagementbutton = "//span[.='User Management ']"; // Locator for confirm deletion button
        click(userManagementbutton, "User management");
        return this;
    }
    public MenuPage clickonusers() {
        String usersOption = "//a[.='Users']"; // Locator for confirm deletion button
        click(usersOption, "Users");
        return this;
    }
    public MenuPage clickonAddusers() {
        String usersOption = "(//button[@type = 'button'])[6]"; // Locator for confirm deletion button
        click(usersOption, "Users");
        return this;
    }
    public MenuPage selectRole() {
        String userRoleDropdown = "(//div[@class = 'oxd-select-text--after'])[1]"; // Locator for confirm deletion button
        click(userRoleDropdown, "Users");
        return this;
    }
    public MenuPage selectuserRole() {
        String userRole = "//span[.='ESS']";
        click(userRole, "Users");
        return this;
    }
    public MenuPage EmployeeName(String Employeename) {
        String employeeNameField = "//input[@placeholder = 'Type for hints...']"; // Locator for Job Category input field
        type("//input[@placeholder = 'Type for hints...']","Fireflink  eJoJz", "Employee name");
        return this;
    }
    public MenuPage selectStatus() {
        String statusDropdown = "(//div[@class = 'oxd-select-text--after'])[2]";
        click(statusDropdown, "Status");
        return this;
    }
    public MenuPage selectStatusdropdown() {
        String statusDropdownvalue = "//span[.='Enabled']";
        click(statusDropdownvalue, "Status");
        return this;
    }
    public MenuPage entername(String username) {
        // Locator for username field
        String nameField = "(//input[@class = 'oxd-input oxd-input--active'])[2]";

        type("(//input[@class = 'oxd-input oxd-input--active'])[2]", "admin1", "Username");
        return this;
    }
    public MenuPage enterpassword(String password) {
        // Locator for username field
        String passwordField = "(//input[@type = 'password'])[1]";

        type("(//input[@type = 'password'])[1]", "Admin@123!", "Password");
        return this;
    }
    public MenuPage enterConfirmpassword(String password) {
        // Locator for username field
        String confirmpasswordField = "(//input[@type = 'password'])[2]";

        type("(//input[@type = 'password'])[1]", "Admin@123!", "Password");
        return this;
    }
    public MenuPage newuserCreation() {
        // Locator for save button
        String userCreationButton = "//button[@class = 'oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']";

        click(userCreationButton, "Save Button");
        return this;
    }
    public MenuPage configuration() {
        String configurationMenuLocator = "//span[.='Configuration ']";
        click(configurationMenuLocator, "Configuration Button");
        return this;
    }
    public MenuPage selectionofOptionalfields() {
        String optionalFieldLocator = "//a[.='Optional Fields']";
        click(optionalFieldLocator, "Save Button");
        return this;
    }
    public MenuPage changeToggleShowDeprecatedFields() {
        String locator = "(//span[@class='oxd-switch-input oxd-switch-input--active --label-right'])[1]";
        check(locator, "Show Deprecated Fields Toggle");
        return this;
    }

    public MenuPage changeToggleShowSSNField() {
        String statusDropdown = "//p[normalize-space()='Show SSN field in Personal Details']//parent::div/div//span";
        click(statusDropdown, "Status");
        return this;
    }

    public MenuPage changeToggleShowSINField() {
        String locator = "//p[normalize-space()='Show SIN field in Personal Details']//parent::div/div//span";
        check(locator, "Show SIN Field Toggle");
        return this;
    }

    public MenuPage changeToggleShowUSTaxExemptions() {
        String locator = "//p[normalize-space()='Show US Tax Exemptions menu']//parent::div/div//span";
        check(locator, "Show U.S. Tax Exemptions Toggle");
        return this;
    }

    public MenuPage creationofOptionalfield() {
        String locator = "//button[@class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']";
        click(locator, "Save Button");
        return this;
    }
    public MenuPage dataImportoption() {
        String dataImportLocator = "//a[.='Data Import']";
        click(dataImportLocator, "DataImport Option");
        return this;
    }
    public MenuPage clickOnBrowseButton() {
        String browseButtonLocator = "//div[.='Browse']";
        click(browseButtonLocator, "Browse Button");
        return this;
    }

    public MenuPage selectFile(String filePath) {
        String fileLocation = "C:\\Users\\DELL\\Downloads\\Files\\importData.csv";
        String fileInputLocator = "//input[@type='file']";
        uploadFile("//input[@type='file']",fileLocation,  "File Upload");
        return this;
    }
    public MenuPage verifyFileNameDisplayed(String expectedFileName) {
        String fileNameLocator = "//div[@class='oxd-file-input-div']";
        verifyExactText(fileNameLocator, "importData.csv");
        return this;
    }
    public MenuPage clickOnUploadButton() {
        String uploadButtonLocator = " //button[.=' Upload ']";
        click(uploadButtonLocator, "Upload Button");
        return this;
    }
    public MenuPage selectReportingmethod() {
        String addReportingMethodButton = "//a[.='Reporting Methods']";
        click(addReportingMethodButton, "Add Reporting Method Button");
        return this;
    }
    public MenuPage navigateToAddReportingMethodForm() {
        // Locator for the Add Reporting Method button
        String addReportingMethodButton = "//button[.=' Add ']";
        click(addReportingMethodButton, "Add Reporting Method Button");
        return this;
    }
    public MenuPage enterName(String reportMethodName) {
        // Locator for the Reporting Method Name input field
        String reportMethodNameField = "//label[text()='Name']/parent::div/following-sibling::div//input";
        typeWithType(reportMethodNameField, "Method report details 1234579", "Report Method Name");
        return this;
    }
    public MenuPage reportCreation() {
        // Locator for the Save Button
        String reportButton = "//button[.=' Save ']";
        click(reportButton, "Save Button");
        return this;
    }
    public MenuPage clickOnreportEditIcon() {
        String editIconLocator = "(//button[contains(@class, 'oxd-icon-button') and contains(@class, 'oxd-table-cell-action-space')]//i[@class='oxd-icon bi-pencil-fill'])[1]";
        click(editIconLocator, "Edit Icon for reportmethod");
        return this;
    }
    public MenuPage modifyNameField(String newName) {
        String nameFieldLocator = "//label[text()='Name']/parent::div/following-sibling::div//input";
       // clear(nameFieldLocator);
        type(nameFieldLocator, "Method value", "Report Method name");
        return this;
    }
    public MenuPage locateDeleteIconreport() {
        String deleteLocator = "(//button[contains(@class, 'oxd-icon-button') and contains(@class, 'oxd-table-cell-action-space')]//i[contains(@class, 'bi-trash')])[1]";
        click(deleteLocator, "delete Button");
        return this;
    }

    public MenuPage clickDeleteIconreport() {
        String deleteIconLocator = "//button[.=' Yes, Delete ']";
        click(deleteIconLocator, "Delete Icon");
        return this;
    }
    public MenuPage clickDeleteCheckbox() {
        String deleteCheckboxLocator = "(//div[contains(@class, 'oxd-table-card-cell-checkbox')]//label)[2]";
        click(deleteCheckboxLocator, "Delete checkbox");
        return this;
    }
    public MenuPage clickConfirmButtongrid() {
        String confirmButtongridLocator = "//button[.=' Delete Selected ']";
        click(confirmButtongridLocator, "Confirm Button");
        return this;
    }
    public MenuPage clickcolumndeletion() {
        String gridlocator = " //button[.=' Yes, Delete ']";
        click(gridlocator, "Delete Icon");
        return this;
    }



}









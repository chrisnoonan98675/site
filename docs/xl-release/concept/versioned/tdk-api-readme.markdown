---
title: Test Development Kit ReadMe
categories:
- xl-release
subject:
- Test Development Kit
tags:
- test development kit
- tdk
- api
- javadoc
weight: 514
---

## This library maintains api's (common functions) which can be used to write platform independent End to End UI Tests against XL Release

    This library uses selenium webdriver approach to act on browser as a normal user would do 

## Sample

```ll
package xlrelease;

import com.xebialabs.pages.*;
import com.xebialabs.specs.BaseTest;
import org.testng.annotations.*;

public class EndToEndTests extends BaseTest {

    @BeforeMethod
    public void testStartUp(){
        System.out.println("called before method");
        LoginPage.login("admin","admin");
    }

    @Test
    public void OpenConfiguration(){
        MainMenu.clickMenu("Settings");
        SubMenu.clickSubMenu("Shared configuration");
        SharedConfigurationPage.openSharedConfiguration("Jenkins: Server");
        SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("Jenkins");
    }
   
    @Test
    public void SaveConfiguration(){
        MainMenu.clickMenu("Settings");
        SubMenu.clickSubMenu("Shared configuration");
        SharedConfigurationPage.openSharedConfiguration("Git: Repository");
        SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("Git");
        SharedConfigurationPropertiesPage.setEditFieldBySequence(1,"Git Title Name");
        SharedConfigurationPropertiesPage.setEditFieldBySequence(2,"Git Url");
        SharedConfigurationPropertiesPage.clickButtonByText("Save");
        SharedConfigurationPropertiesPage.isNewConfigurationSaved().isSharedConfigurationPageVisible("Git: Repository");
    }

    @Test
    public void TestConfiguration(){
        MainMenu.clickMenu("Settings");
        SubMenu.clickSubMenu("Shared configuration");
        SharedConfigurationPage.openSharedConfiguration("Git: Repository");
        SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("Git");
        SharedConfigurationPropertiesPage.setEditFieldBySequence(1,"Git Title Name");
        SharedConfigurationPropertiesPage.setEditFieldBySequence(2,"Git Url");
        SharedConfigurationPropertiesPage.clickElementById("authenticationMethod"); // clicking the element so that select field will be visible on next step
        SharedConfigurationPropertiesPage.setOptionFromSelectFieldBySequence(1,"None");
        SharedConfigurationPropertiesPage.clickButtonByText("Test");
        SharedConfigurationPropertiesPage.checkConnectionStatusShouldContain("Can't connect");
    }

    @Test
    public void VerifyXlDeplyPluginAbleToDeploy(){
        MainMenu.clickMenu("Settings");
        SubMenu.clickSubMenu("Shared configuration");
        SharedConfigurationPage.openSharedConfiguration("XL Deploy Server");
        SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("XL Deploy");
        SharedConfigurationPropertiesPage.setEditFieldBySequence(1,"XL Deploy");
        SharedConfigurationPropertiesPage.setEditFieldBySequence(2,"http://xl-deploy-nightly.xebialabs.com:4516/");
        SharedConfigurationPropertiesPage.typeElementById("username","admin");
        SharedConfigurationPropertiesPage.typeElementById("password","admin");
        SharedConfigurationPropertiesPage.clickButtonByText("Test");
        SharedConfigurationPropertiesPage.checkConnectionStatusShouldContain("XL Deploy Server is available");
        SharedConfigurationPropertiesPage.clickButtonByText("Save");
        MainMenu.clickMenu("Design");
        SubMenu.clickSubMenu("Templates");
        TemplateListPage.clickNewTemplate();
        CreateTemplatePage
                .createTemplateByName("Xl Deploy Template")
                .addTask("XL Deploy","XL Deploy","Deploy")
                .selectItemByIndex(1,"XL Deploy")
                .typeStringByIndex(1,"ZApp very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long string/1.0")
                .typeStringByIndex(2,"TestDirEnv/Env2")
                .closeTaskDetails()
                .newReleaseFromTemplate()
                .createReleaseByName("XL Deploy Release")
                .startRelease()
                .waitTillReleaseCompletes(15);
    }

    @AfterMethod
    public void logout(){
        System.out.println("called after method");
        MainMenu.logout();
    }
}
```
    
## Prerequisite 

 - XL Release server must be running
 - v0.0.5 is compatible with XLR 8.0.x
 - v0.0.6 + is compatible with XLR 8.1.x
 - backend required to test your plugin end to end must be up and running

## How to use 

 - add maven plugin as testcompile dependency to your gradle project [download](https://mvnrepository.com/artifact/com.xebialabs.gradle.plugins/xl-test-api)
 - add this test task inside your `build.gradle`
  
         
         test {
             useTestNG() {
                 //set TestNG output dir
                 outputDirectory = file("$project.buildDir//testngOutput")
                 //required to turn on TestNG reports
                 useDefaultListeners = true
             }
         }
         
 

## To Run the tests against your own server / browser preference 

 - Set environment variable `WEB_SERVER` pointing to your XLR instance 
    - default is `http://localhost:5516`
    
 - Set environment variable `BROWSER` to either `chrome` or `firefox`
    - default is `chrome`
 
 - To launch the chrome browser in headless mode set env variable `CHROME_HEADLESS_MODE` to `true`
 - To launch the firefox browser in headless mode set env variable `FIREFOX_HEADLESS_MODE` to `true`   
 - Run `./gradlew test` this will launch the tests against the server 
 
## view the test report - compatible with TRAVIS-CI and JENKINS

 - Once tests are completed you will see the test report inside `build/testngOutput/emailable-report.html`
 
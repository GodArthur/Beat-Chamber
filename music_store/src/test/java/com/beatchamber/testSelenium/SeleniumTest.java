/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.testSelenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author kibra
 */
public class SeleniumTest {
    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        // Normally an executable that matches the browser you are using must
        // be in the classpath. The webdrivermanager library by Boni Garcia
        // downloads the required driver and makes it available
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    /**
     * The most basic Selenium test method that tests to see if the page name
     * matches a specific name.
     *
     * @throws Exception
     */
    @Test
    public void testSimple() throws Exception {
        // And now use this to visit this project
        driver.get("http://localhost:8080/music_store/");

        // Wait for the page to load, timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // Wait for the page to load, timeout after 10 seconds
        wait.until(ExpectedConditions.titleIs("Beat Chamber"));

        List<WebElement> inputElements = new ArrayList<WebElement>();
        
        WebElement inputElement = null;
        
         //              select survey              
        
        //select survey answer
        /*
        inputElement = driver.findElement(By.className("ui-radiobutton-icon"));
        inputElement.click();
        
        inputElement = driver.findElement(By.id("j_idt36:completeSurvey"));
        inputElement.click();*/
        
        
        //              add item to cart              
        
        inputElements = driver.findElements(By.tagName("button"));
        inputElements.get(3).click();
        
        inputElements = driver.findElements(By.tagName("button"));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/music_store/browse_music.xhtml"));
        System.out.println(inputElements.get(0).getText()+" ###");
        inputElements.get(0).click();
        
        /*
        //inputElement = driver.findElement(By.id("j_idt50:addToCartAlbum"));
        inputElement = getButton("Add to Cart");
        inputElement.click();
        
        //return to home
        inputElement = driver.findElement(By.className("j_idt9:logo-Icon"));
        inputElement.click();
        
        
        //select the album Bones in the Ocean
        selected = null ;
        inputElements = driver.findElements(By.className("p"));
        for(WebElement item:inputElements){
            if(item.getText().equals("Bones in the Ocean")){
                selected = item;
            }
        }
        
        if(selected != null){
            selected.click();
        }
        
        
        
        
        inputElement = driver.findElement(By.id("j_idt51:addToCartAlbum"));
        inputElement.click();
        
        
        //return to home
        inputElement = driver.findElement(By.className("j_idt9:logo-Icon"));
        inputElement.click();
        
        
        //              create user              
        
        //go to log in page
        inputElement = driver.findElement(By.id("j_idt9:loginBtnNavBar"));
        inputElement.click();
        
        
        inputElement = driver.findElement(By.id("j_idt39:redirectToSignupId"));
        inputElement.click();
        
        inputElement = driver.findElement(By.id("form:username"));
        inputElement.sendKeys("dawsonTestUser");
        
        inputElement = driver.findElement(By.id("form:email"));
        inputElement.sendKeys("mydawson@Email.com");
        
        inputElement = driver.findElement(By.id("form:password"));
        inputElement.sendKeys("thisIsMyPassword2021");
        
        inputElement = driver.findElement(By.id("form:re-password"));
        inputElement.sendKeys("thisIsMyPassword2021");
        
        inputElement = driver.findElement(By.id("form:first_name"));
        inputElement.sendKeys("bullwinkle");
        
        inputElement = driver.findElement(By.id("form:last_name"));
        inputElement.sendKeys("J.moose");
        
        inputElement = driver.findElement(By.id("form:registerSubmitBtn"));
        inputElement.click();
        
        
        //              making an order              
        
        inputElement = driver.findElement(By.id("j_idt9:cartIconNav"));
        inputElement.click();
        
        inputElement = driver.findElement(By.id("j_idt44:j_idt46"));
        inputElement.click();
        
        inputElement = driver.findElement(By.id("j_idt38:creditC"));
        inputElement.sendKeys("4111111111111111");
        
        inputElement = driver.findElement(By.id("j_idt38:expDate"));
        inputElement.sendKeys("12/2222");
        
        inputElement = driver.findElement(By.id("j_idt38:cvv"));
        inputElement.sendKeys("123");
        
        inputElement = driver.findElement(By.id("j_idt38:nameCard"));
        inputElement.sendKeys("Bobby");
        
        inputElement = driver.findElement(By.id("j_idt38:address"));
        inputElement.sendKeys("dawson 3223");
        
        inputElement = driver.findElement(By.id("j_idt38:City"));
        inputElement.sendKeys("Montreal");
        
        
        inputElement = driver.findElement(By.className("j_idt38:j_idt74"));
        inputElement.click();
        
        
        inputElement = driver.findElement(By.className("j_idt9:cartIconNav"));
        inputElement.click();
        
        */
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/music_store/index.xxhtml"));

    }
    
    private WebElement getButton(String text){
        WebElement selected = null ;
        
        List<WebElement> inputElements = new ArrayList<WebElement>();
        inputElements = driver.findElements(By.tagName("a"));
        
        for(WebElement item:inputElements){
            if(item.getText().equals("")){
                selected = item;
            }
        }
        
        return selected;
    }

    @After
    public void shutdownTest() {
        driver.quit();
    }
}

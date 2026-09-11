package com.ecommerce.tests;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.ecommerce.base.DriverFactory;
import com.ecommerce.utilities.ConfigReader;

public class BaseTest {

    @Parameters("xmlBrowser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String xmlBrowser) {
        
        // 1. माइक्रोसॉफ्ट के पुराने टूटे हुए लिंक को नए मिरर यूआरएल से बदलें (Fix for Edge 152)
        System.setProperty("SE_DRIVER_MIRROR_URL", "https://msedgedriver.microsoft.com");
        
        if (xmlBrowser != null && !xmlBrowser.isEmpty()) {
            System.setProperty("browser", xmlBrowser); 
        }
        
        DriverFactory.initDriver();
        WebDriver driver = DriverFactory.getDriver();
        
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        
        String targetUrl = ConfigReader.getProperty("url");
        driver.get(targetUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    public static String captureScreenshot(String testName) {
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        try {
            java.io.File folder = new java.io.File(System.getProperty("user.dir") + "/screenshots/");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            
            TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
            java.io.File source = ts.getScreenshotAs(OutputType.FILE);
            java.io.File destination = new java.io.File(screenshotPath);
            FileHandler.copy(source, destination);
        } catch (Exception e) {
            System.out.println("स्क्रीनशॉट लेते समय एरर आया: " + e.getMessage());
        }
        return screenshotPath; 
    }
}

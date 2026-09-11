package com.ecommerce.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.ecommerce.utilities.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static void initDriver() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = ConfigReader.getProperty("browser");
        }

        if (browser == null) {
            throw new RuntimeException("Browser is not configured!");
        }

        browser = browser.toLowerCase().trim();

        if (tlDriver.get() == null) {
            if (browser.equals("chrome")) {
                
                // 🛠️ FIX: WebDriverManager manually local machine par setup kar dega bina crash kiye
                WebDriverManager.chromedriver().setup(); 

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new"); 
                options.addArguments("--disable-gpu"); 
                options.addArguments("--window-size=1920,1080"); 
                options.addArguments("--start-maximized"); 
                options.addArguments("--remote-allow-origins=*");
                tlDriver.set(new ChromeDriver(options));
                
            } else if (browser.equals("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");
                tlDriver.set(new FirefoxDriver(options));
                
            } else if (browser.equals("edge")) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless");
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                tlDriver.set(new EdgeDriver(options));
                
            } else {
                throw new RuntimeException("Unsupported browser type: " + browser);
            }
        }
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove(); 
        }
    }
}
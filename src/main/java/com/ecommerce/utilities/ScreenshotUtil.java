package com.ecommerce.utilities;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {

        String screenshotPath = System.getProperty("user.dir")
                + "/test-output/screenshots/" + testName + ".png";

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
   
        File destination = new File(screenshotPath);
        destination.getParentFile().mkdirs();

        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
}
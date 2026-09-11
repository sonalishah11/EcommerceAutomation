package com.ecommerce.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // synchronized block: इससे दोनों थ्रेड्स एक साथ रिपोर्ट ऑब्जेक्ट को क्रैश नहीं कर पाएंगे
        synchronized (TestListener.class) {
            if (extent == null) {
                ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
                sparkReporter.config().setDocumentTitle("Ecommerce Automation Report");
                sparkReporter.config().setReportName("Cross Browser Test Results");
                sparkReporter.config().setTheme(Theme.DARK);

                extent = new ExtentReports();
                extent.attachReporter(sparkReporter);
                extent.setSystemInfo("Host Name", "Localhost");
                extent.setSystemInfo("Environment", "QA");
                extent.setSystemInfo("User", "Sonali");
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestContext().getName() + " - " + result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Test Passed Successfully!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().getRetryAnalyzer(result) != null && 
            result.getMethod().getRetryAnalyzer(result).retry(result)) {
            
            extent.removeTest(extentTest.get());
            System.out.println("टेस्ट फेल हुआ, लेकिन दोबारा प्रयास (Retry) किया जा रहा है: " + result.getName());
        } else {
            extentTest.get().fail(result.getThrowable());
            String screenshotPath = com.ecommerce.tests.BaseTest.captureScreenshot(result.getMethod().getMethodName());
            extentTest.get().addScreenCaptureFromPath(screenshotPath, "Final Failed Test Screen");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped!");
    }

    @Override
    public void onFinish(ITestContext context) {
        synchronized (TestListener.class) {
            if (extent != null) {
                extent.flush();
            }
        }
    }
}

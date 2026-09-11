package com.ecommerce.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxTry = 2; // फेल होने पर टेस्ट को 2 बार और चलाएगा

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) { // अगर टेस्ट फेल हुआ है
            if (count < maxTry) {
                count++;
                // TestNG को बताएं कि इस टेस्ट को दोबारा रन करना है
                return true; 
            }
        }
        return false;
    }
}

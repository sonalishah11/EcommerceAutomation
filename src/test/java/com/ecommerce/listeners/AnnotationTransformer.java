package com.ecommerce.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // यहाँ मेथड का नाम सही करके setRetryAnalyzer कर दिया गया है
        annotation.setRetryAnalyzer(Retry.class);
    }
}

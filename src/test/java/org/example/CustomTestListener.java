package org.example;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomTestListener extends TestListenerAdapter {
    private List<String> passedTests = new ArrayList<>();
    private List<String> failedTests = new ArrayList<>();

    @Override
    public void onTestSuccess(ITestResult tr) {
        passedTests.add(tr.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        failedTests.add(tr.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        System.out.println("\nTest Summary:");
        System.out.println("-------------------------------------------------");
        System.out.println("| Test Case                                 | Result  |");
        System.out.println("-------------------------------------------------");

        for (String testName : passedTests) {
            System.out.printf("| %-40s | Passed  |\n", testName);
        }

        for (String testName : failedTests) {
            System.out.printf("| %-40s | Failed  |\n", testName);
        }

        System.out.println("-------------------------------------------------");
    }
}
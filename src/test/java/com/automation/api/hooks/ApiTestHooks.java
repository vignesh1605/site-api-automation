package com.automation.api.hooks;

import com.automation.api.logging.ApiLog;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

public abstract class ApiTestHooks {

    @BeforeSuite(alwaysRun = true)
    public void startSuiteLogging() throws IOException {
        ApiLog.open();
        ApiLog.info("SUITE START");
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method method) {
        ApiLog.info("START " + getClass().getSimpleName() + "." + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestResult(ITestResult result) {
        String status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "PASS";
            case ITestResult.FAILURE -> "FAIL";
            case ITestResult.SKIP -> "SKIP";
            default -> "STATUS_" + result.getStatus();
        };

        String testName = result.getTestClass().getRealClass().getSimpleName()
                + "." + result.getMethod().getMethodName();
        long durationMs = result.getEndMillis() - result.getStartMillis();

        Throwable error = result.getThrowable();
        String errorDetails = error == null
                ? ""
                : " error=" + error.getClass().getSimpleName();

        ApiLog.info("%s %s durationMs=%d%s".formatted(
                status, testName, durationMs, errorDetails));
    }

    @AfterSuite(alwaysRun = true)
    public void stopSuiteLogging() {
        ApiLog.info("SUITE END");
        ApiLog.close();
    }
}
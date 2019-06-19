package com.scenario_projects.mq_back_stage.logging;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
        CustomReporter.log("Test is started");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        CustomReporter.log("Test is finished");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        CustomReporter.log("Test is finished");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}

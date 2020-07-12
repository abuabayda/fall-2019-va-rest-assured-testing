package com.DBUtil;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.jupiter.api.extension.*;

public class ExtentConfig implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback, AfterTestExecutionCallback {
    static ExtentReports reports;
    public static ExtentTest test;

    //run before each class
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        //thi line create a report under te tes-output folder with name of class
        String filename = System.getProperty("user.dir") + "/test-output/" + context.getDisplayName() + "_Results.html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filename);
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

    }

    //runs before each test
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        test = reports.createTest(context.getDisplayName());

        test.log(Status.INFO, context.getDisplayName() + " - started");

    }

    //after each test in that class
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (!context.getExecutionException().isPresent()) {
            test.pass(context.getDisplayName() + " - passed");
        } else if (context.getExecutionException().get().toString().contains("AssertionError")) {
            test.fail(context.getExecutionException().get().toString());
            test.fail(context.getExecutionException().get().getCause());
        } else {
            test.fatal(context.getExecutionException().get().toString());
            test.fatal(context.getExecutionException().get().getCause());
        }
    }

    //after each class
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        reports.flush();
    }
}
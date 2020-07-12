package com.cbt.runner;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Automated api tests")
@SelectPackages("com.cbt.tests.day11_cookie_and_extend_report")
public class BatchRunner {
}

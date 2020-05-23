package com.bayer.digitalfarming.frontend.selenium.utils.reports;

import java.util.Iterator;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onFinish(ITestContext context) { 
    	 Iterator<ITestResult> listOfSkippedTests = context.getSkippedTests()
    			 .getAllResults().iterator();
         while (listOfSkippedTests.hasNext()) {
             ITestResult skippedTest = listOfSkippedTests.next();
             ITestNGMethod method = skippedTest.getMethod();
             if (context.getSkippedTests().getResults(method).size() > 0) {
            	 listOfSkippedTests.remove();}
         }
    }

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
}

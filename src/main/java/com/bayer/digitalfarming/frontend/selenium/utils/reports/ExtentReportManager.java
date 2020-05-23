package com.bayer.digitalfarming.frontend.selenium.utils.reports;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bayer.digitalfarming.frontend.selenium.utils.config.GlobalProperties;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

public class ExtentReportManager implements ITestListener, IReporter {
	/*
	 * private static final String OUTPUT_FOLDER = "test-output/"; private static
	 * final String FILE_NAME = "Extent.html";
	 */

	private ExtentReports extent;
	private ExtentHtmlReporter htmlReporter;
	private ExtentTest test;
	private static WebDriver driver;
	private static Multimap<String, String> testMap = ArrayListMultimap.create();
	private static Multimap<String, String> logMap = LinkedHashMultimap.create();
	private String filepath;
	private String targetEnv;
	private String countryLocale;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		init();

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getFailedTests(), Status.FAIL);
				buildTestNodes(context.getSkippedTests(), Status.SKIP);
				buildTestNodes(context.getPassedTests(), Status.PASS);

			}
		}

		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}

		extent.flush();
	}

	private void init() {

		extent = new ExtentReports();
		Path path = Paths.get(System.getProperty("user.dir") + "/Reports");
		//System.out.println("Report Path is first:"+path);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		File file = new File(System.getProperty("user.dir") + "/Reports");
		//System.out.println("Report Path is Second :"+path);
		if (!file.exists()) {
			file.mkdir();
		}

		String packName = System.getProperty("suiteFile");

		if (packName != null) {
			String country;
			targetEnv = System.getProperty("targetEnv").toUpperCase();
			countryLocale = System.getProperty("locale").replace("_", "-").toUpperCase();
			String reportName = packName.replace("testng", "").replace(".xml", "_");
			filepath = "Reports/FM_" + targetEnv + "_" + reportName + countryLocale + "_"
					+ dateFormat.format(cal.getTime()) + ".html";
			extent.setSystemInfo("Environment", targetEnv);
			extent.setSystemInfo("Country", countryLocale);
			htmlReporter = new ExtentHtmlReporter(filepath);
			htmlReporter.loadXMLConfig(new File(GlobalProperties.EXTENT_REPORT_CONFIG));
			switch (countryLocale) {
			case "EN-DE":
			case "DE-DE":
				country = "Germany-";
				break;
			case "EN-FR":
			case "FR-FR":
				country = "France-";
				break;
			case "EN-AT":
			case "DE-AT":
				country = "Austria-";
				break;
			case "EN-NL":
			case "NL-NL":
				country = "Netherlands-";
				break;
			case "EN-UA":
			case "UK-UA":
				country = "Ukraine-";
				break;
			case "EN-PL":
			case "PL-PL":
				country = "Poland-";
				break;
			case "EN-CA":
				country = "Canada-";
				break;
			case "EN-US":
				country = "USA-";
				break;
			case "EN-AR":
			case "ES-AR":
				country = "Argentina-";
				break;
			case "EN-BE":
			case "FR-BE":
			case "NL-BE":
				country = "Belgium-";
				break;
			case "EN-HU":
			case "HU-HU":
				country = "Hungary-";
				break;
			case "EN-GB":
				country = "Great Britain-";
				break;
			case "EN-CZ":
			case "CS-CZ":
				country = "Czech Republic-";
				break;
			default:
				country = "";
				break;
			}
			htmlReporter.config().setReportName(
					"<img src='data:image/jpeg;base64,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' />"
							+ "" + country + "Test Automation Report");
			filepath = "Reports/FM_" + targetEnv + reportName + countryLocale + dateFormat.format(cal.getTime())
					+ ".html";

		} else {
			filepath = "Reports/FieldManager_" + dateFormat.format(cal.getTime()) + ".html";
			htmlReporter = new ExtentHtmlReporter(filepath);
			htmlReporter.loadXMLConfig(new File(GlobalProperties.EXTENT_REPORT_CONFIG));
		}

		System.out.println(
				"*****************************************************************************************************!!!!!!!!!!!!");
		System.out.println(filepath);

		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);

		extent.attachReporter(htmlReporter);
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();

		extent.setSystemInfo("Browser", capabilities.getBrowserName().toUpperCase());
		extent.setSystemInfo("Browser Version", capabilities.getVersion().toUpperCase());
		extent.setSystemInfo("Java Version", System.getProperty("java.version").toUpperCase());
		extent.setSystemInfo("OS", System.getProperty("os.name").toUpperCase());

		extent.setReportUsesManualConfiguration(true);
	}

	private void buildTestNodes(IResultMap results, Status status) {

		Set<ITestResult> allTestResults = results.getAllResults();
		List<ITestResult> tests = new ArrayList<ITestResult>(allTestResults);
		Collections.sort(tests, new Comparator<ITestResult>() {
			@Override
			public int compare(ITestResult o1, ITestResult o2) {
				long t1 = o1.getStartMillis() - o2.getStartMillis();
				return (int) (t1);
			}
		});

		if (tests.size() > 0) {
			for (ITestResult result : tests) {
				String testname = result.getMethod().getDescription();
				test = extent.createTest(testname);

				for (String log : logMap.get((result.getMethod().getMethodName()))) {
					test.pass(log);
				}

				String methodname = result.getMethod().getMethodName();
				for (String screenshot : testMap.get(methodname)) {
					switch (status.toString().toLowerCase()) {
					case "pass":
						test.assignCategory(result.getTestContext().getName());
						Markup markUp = MarkupHelper.createLabel(
								methodname + " is " + status.toString().toLowerCase() + "ed", ExtentColor.GREEN);
						test.pass(markUp);
						break;
					case "fail":
						test.assignCategory(result.getTestContext().getName());
						test.log(status, result.getThrowable().getMessage());
						test.fail("Screenshot : " + screenshot);
						Markup markUpFail = MarkupHelper.createLabel(
								methodname + " is " + status.toString().toLowerCase() + "ed", ExtentColor.RED);
						test.fail(markUpFail);
						break;
					case "skip":
						test.assignCategory(result.getTestContext().getName());
						test.skip("Screenshot : " + screenshot);
						break;
					default:
						test.assignCategory(result.getTestContext().getName());
						break;
					}
				}

//				if (result.getThrowable() != null) {
//					test.log(status, result.getThrowable());
//				} else {
//					Markup markUp = MarkupHelper.createLabel(methodname + " is " + status.toString().toLowerCase() + "ed",
//							ExtentColor.GREEN);
//					test.pass(markUp);
//				}

				test.getModel().setStartTime(getTime(result.getStartMillis()));
				test.getModel().setEndTime(getTime(result.getEndMillis()));

			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static void log(String method, String logSteps) {
		logMap.put(method, logSteps);
	}

	public static void setDriver(WebDriver webDriver) {
		driver = webDriver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public String takesScreenshot() {

		String encodedImage = "";
		try {
			encodedImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.format("<a href='data:image/png;base64,%s' target='_blank'>Click here</a></br>", encodedImage);

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		testMap.put(result.getMethod().getMethodName(), takesScreenshot());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		testMap.put(result.getMethod().getMethodName(), takesScreenshot());
	}

	@Override
	public void onTestStart(ITestResult result) {
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

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}

package base;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.openqa.selenium.WebDriver;
import utilities.ScreenshotUtil;

/**
 * Custom TestNG listener to handle test lifecycle events and logging.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Log when tests start, succeed, or fail.</li>
 *   <li>Capture and attach screenshots to reports for both failures and successes (optional).</li>
 *   <li>Fetch the correct {@link WebDriver} from the current test instance using {@link BaseTest#getDriver()}.</li>
 * </ul>
 */
public class Listeners implements ITestListener {

    /**
     * Retrieves the {@link WebDriver} instance tied to the current test thread.
     *
     * @param result the test result containing the instance reference
     * @return the driver if the test class extends {@link BaseTest}, otherwise {@code null}
     */
    private WebDriver driverFrom(ITestResult result) {
        Object inst = result.getInstance();
        if (inst instanceof BaseTest) {
            return ((BaseTest) inst).getDriver(); // ThreadLocal-backed driver
        }
        return null;
    }

    /**
     * Invoked when a test method starts.
     *
     * @param result metadata about the running test
     */
    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log("Test started: " + result.getMethod().getMethodName());
    }

    /**
     * Invoked when a test method fails.
     * Captures a screenshot of the failure state.
     *
     * @param result metadata about the failed test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("Test Failed: " + result.getMethod().getMethodName());
        captureScreenshot(result);
    }

    /**
     * Invoked when a test method succeeds.
     * Optionally captures a screenshot of the passing state.
     *
     * @param result metadata about the passed test
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log("Test Passed: " + result.getMethod().getMethodName());
        captureScreenshot(result); // keep if you want screenshots on pass
    }

    /**
     * Helper method to capture a screenshot and embed it into the report.
     *
     * @param result metadata about the test (used for driver retrieval and naming the file)
     */
    private void captureScreenshot(ITestResult result) {
        try {
            WebDriver drv = driverFrom(result);
            if (drv == null) return;

            String path = ScreenshotUtil.take(drv, result.getMethod().getMethodName());
            if (path != null) {
                System.setProperty("org.uncommons.reportng.escape-output", "false");
                Reporter.log("<a href='" + path + "'> <img src='" + path + "' height='200' width='200'/> </a>");
            }
        } catch (Exception e) {
            // optionally log error if screenshot fails
        }
    }
}

package base;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ScreenshotUtil;

/**
 * BaseTest is a reusable foundation for TestNG Selenium tests.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Load configuration and locator properties once per JVM.</li>
 *   <li>Initialize a browser driver per test method (Chrome/Firefox/Edge).</li>
 *   <li>Expose a thread-safe {@link WebDriver} via {@link #getDriver()} for parallel runs.</li>
 *   <li>Navigate to the configured <code>testurl</code> and maximize the window.</li>
 *   <li>Capture a screenshot on failure and ensure clean driver teardown.</li>
 * </ul>
 * <p>
 * Usage:
 * <pre>
 *   public class SomeTest extends BaseTest {
 *       @Test
 *       public void myTest() {
 *           getDriver().findElement(...);
 *       }
 *   }
 * </pre>
 */
public class BaseTest {
	
    /**
     * Thread-safe container for per-thread WebDriver instances.
     * <p>Enables parallel test execution without driver collisions.</p>
     */
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    /**
     * Returns the thread-bound {@link WebDriver} instance set during {@link #setup(String)}.
     * @return the current thread's WebDriver, or {@code null} if not initialized
     */
    protected WebDriver getDriver() { return TL_DRIVER.get(); }

    /**
     * (Legacy) reference to the current driver instance.
     * <p>Kept for compatibility; prefer {@link #getDriver()}.</p>
     */
	public static WebDriver driver;

    /**
     * Reader for <code>config.properties</code> (environment & runtime settings).
     */
	public static FileReader fr1;

    /**
     * Reader for <code>locators.properties</code> (element selectors).
     */
	public static FileReader fr2;

    /**
     * Loaded key/value pairs from <code>config.properties</code>.
     * <p>Expected keys include (example): <code>testurl</code>.</p>
     */
	public static Properties prop = new Properties();

    /**
     * Loaded key/value pairs from <code>locators.properties</code>.
     * <p>Holds page object locator IDs/XPaths, e.g., <code>SEARCH_ID</code>.</p>
     */
	public static Properties loc = new Properties();
	
    /**
     * Creates a fresh browser session for each test method.
     * <p>
     * Behavior:
     * <ul>
     *   <li>Lazy-loads <code>config.properties</code> and <code>locators.properties</code> once.</li>
     *   <li>Starts the requested browser (chrome, firefox, or edge).</li>
     *   <li>Registers the driver in a {@link ThreadLocal} for thread safety.</li>
     *   <li>Maximizes the window and opens the URL from <code>prop.testurl</code>.</li>
     * </ul>
     * </p>
     *
     * @param browser the browser name provided by TestNG XML parameter (e.g., {@code chrome}, {@code firefox}, {@code edge})
     * @throws IOException if either properties file cannot be read
     * @throws IllegalArgumentException if the provided browser name is unsupported
     */
	@Parameters("browser")
	@BeforeMethod
	public void setup(String browser) throws IOException {
		if (prop.isEmpty() || loc.isEmpty()) {
			fr1 = new FileReader(System.getProperty("user.dir")+ "\\src\\test\\resources\\configfiles\\config.properties");
			fr2 = new FileReader(System.getProperty("user.dir")+ "\\src\\test\\resources\\configfiles\\locators.properties");
			prop.load(fr1);
			loc.load(fr2);
		}
		
		if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        TL_DRIVER.set(driver);
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("testurl"));
	}
			
//		if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//			driver.get(prop.getProperty("testurl"));
//			driver.manage().window().maximize();
//		} else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//			driver.get(prop.getProperty("testurl"));
//			driver.manage().window().maximize();
//		} else if (prop.getProperty("browser").equalsIgnoreCase("edge")) {
//			WebDriverManager.edgedriver().setup();
//			driver = new EdgeDriver();
//			driver.get(prop.getProperty("testurl"));
//			driver.manage().window().maximize();

    /**
     * Cleans up after each test method.
     * <p>
     * Steps:
     * <ol>
     *   <li>If the test failed, capture a screenshot via {@link ScreenshotUtil#take(WebDriver, String)}.</li>
     *   <li>Quit the driver if present.</li>
     *   <li>Remove the driver from the {@link ThreadLocal} to prevent leaks.</li>
     * </ol>
     * </p>
     *
     * @param result the TestNG result for the just-finished test, used to check success/failure and test method name
     */
	@AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (!result.isSuccess() && getDriver() != null) {
                ScreenshotUtil.take(getDriver(), result.getMethod().getMethodName());
            }
        } catch (Exception ignore) { /* optionally log */ }
        finally {
            WebDriver d = getDriver();
            if (d != null) {
                try { d.quit(); } catch (Exception e) { /* optionally log */ }
            }
            TL_DRIVER.remove();
        }
    }

}

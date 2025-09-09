package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

/**
 * Utility class for capturing screenshots during test execution.
 * <p>
 * Screenshots are saved under the <code>/screenshots</code> directory
 * of the project with a timestamp and test method name.
 * </p>
 */
public class ScreenshotUtil {

    /**
     * Captures a screenshot of the current browser window.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Take a screenshot using {@link TakesScreenshot}.</li>
     *   <li>Generate a unique filename using the provided test name and a timestamp.</li>
     *   <li>Save the screenshot under <code>/screenshots</code> folder in the project root.</li>
     * </ol>
     * </p>
     *
     * @param driver the active {@link WebDriver} instance
     * @param name   the base name (e.g., test method name) to include in the screenshot filename
     * @return the absolute path to the saved screenshot file, or {@code null} if capture fails
     */
    public static String take(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = System.getProperty("user.dir") + "\\screenshots\\" + name + "_" + ts + ".png";
            FileUtils.copyFile(src, new File(path));
            return path;
        } catch (Exception e) {
            return null;
        }
    }
}

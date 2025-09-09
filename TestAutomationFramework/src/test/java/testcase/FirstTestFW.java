package testcase;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.readXLSdata;

/**
 * Test case class for validating search functionality using Selenium + TestNG.
 * <p>
 * Key features:
 * <ul>
 *   <li>Reads test data from Excel via {@link readXLSdata} DataProvider.</li>
 *   <li>Searches for a given word and validates related sections/items.</li>
 *   <li>Asserts that each search result contains the search word.</li>
 *   <li>Demonstrates navigation across multiple result pages (Page 1 → Page 2 → Page 3).</li>
 *   <li>Logs key steps and counts using {@link Reporter} for reporting.</li>
 * </ul>
 */
public class FirstTestFW extends BaseTest {

    /**
     * Executes a search for the given word and validates the results.
     * <p>
     * Flow:
     * <ol>
     *   <li>Wait for search box and perform search.</li>
     *   <li>Validate related sections/items on Page 1.</li>
     *   <li>Navigate to Page 2, wait for results, and scroll.</li>
     *   <li>Navigate to Page 3, wait for results, and scroll.</li>
     *   <li>Compare result counts between Page 2 and Page 3.</li>
     * </ol>
     *
     * @param WORD_SEARCH the word to search, provided by {@link readXLSdata#datatest()}
     */
    @Test(dataProviderClass = readXLSdata.class, dataProvider = "datatest")
    public void SearchTest(String WORD_SEARCH) {

        int waitSeconds = Integer.parseInt(prop.getProperty("wait_timeout_seconds"));
        int minSections = Integer.parseInt(prop.getProperty("min_related_sections"));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitSeconds));

        By searchBox      = By.id(loc.getProperty("SEARCH_ID"));
        By resultsLocator = By.xpath(loc.getProperty("SEARCH_RESULTS"));
        By itemsLocator   = By.xpath(loc.getProperty("SEARCH_ITEMS_RESULTS"));
        By page2Btn       = By.xpath(loc.getProperty("PAGE_2_BTN"));
        By page3Btn       = By.xpath(loc.getProperty("PAGE_3_BTN"));

        // --- Search ---
        wait.until(ExpectedConditions.elementToBeClickable(searchBox)).sendKeys(WORD_SEARCH);
        getDriver().findElement(searchBox).submit();

        // --- Wait results + scroll bottom (Page 1) ---
        List<WebElement> relatedSections = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultsLocator));
        scrollToBottom();
        
        System.out.println("Number of related sections: " + relatedSections.size());
        Reporter.log("Number of related sections: " + relatedSections.size());
        
        // --- Validate related sections/items ---
        Assert.assertTrue(relatedSections.size() >= minSections,
                "Expected at least " + minSections + " related sections, but found: " + relatedSections.size());
        Reporter.log("Found related search sections: " + relatedSections.size());

        for (WebElement section : relatedSections) {
            List<WebElement> items = section.findElements(itemsLocator);
            for (WebElement item : items) {
                Assert.assertTrue(item.getText().toLowerCase().contains(WORD_SEARCH.toLowerCase()),
                        "Item does not contain search word: " + item.getText());
            }
        }
        Reporter.log("\nAll related search items contain the word: " + WORD_SEARCH);

        // === Navigate to Page 2 ===
        WebElement oldFirstResultP1 = relatedSections.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(page2Btn)).click();

        // Wait for page change (staleness of old result), then for new results
        wait.until(ExpectedConditions.stalenessOf(oldFirstResultP1));
        
        System.out.println("PASS: Navigated to page 2 of search results.");
        List<WebElement> searchResultsPage2 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultsLocator));
        
        System.out.println("Number of results on Page 2: " + searchResultsPage2.size());
        Reporter.log("Number of results on Page 2: " + searchResultsPage2.size());
        
        // Scroll to bottom on Page 2
        scrollToBottom();

        // === Navigate to Page 3 ===
        WebElement oldFirstResultP2 = searchResultsPage2.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(page3Btn)).click();

        wait.until(ExpectedConditions.stalenessOf(oldFirstResultP2));
        System.out.println("PASS: Navigated to page 3 of search results.");
        
        List<WebElement> searchResultsPage3 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultsLocator));
        
        System.out.println("Number of results on Page 3: " + searchResultsPage3.size());
        Reporter.log("Number of results on Page 3: " + searchResultsPage3.size());
        
        // Scroll to bottom on Page 3
        scrollToBottom();
        
        // --- Compare counts Page 2 vs Page 3 ---
        Assert.assertEquals(searchResultsPage2.size(), searchResultsPage3.size(),
                "Number of results on Page 2 and Page 3 are not equal!");
        Reporter.log("\nPage 2 results: " + searchResultsPage2.size() + " | Page 3 results: " + searchResultsPage3.size());
    }

    /**
     * Scrolls to the bottom of the page using JavaScript execution.
     * <p>Useful for lazy-loaded search results and consistent UI behavior.</p>
     */
    private void scrollToBottom() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
}

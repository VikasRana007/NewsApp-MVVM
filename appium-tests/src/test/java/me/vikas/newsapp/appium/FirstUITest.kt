package me.vikas.newsapp.appium

import io.appium.java_client.AppiumBy
import org.junit.Assert.assertTrue
import org.junit.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class FirstUITest : BaseAppiumTest() {

    @Test
    fun testNavigationToTopHeadlines() {
        val wait = WebDriverWait(driver, Duration.ofSeconds(20))

        // 1. Splash screen will appear first, we need to wait for the Dashboard to load.
        // We know Dashboard is loaded when the "Top Headlines" card appears.
        // We added `.testTag(item.title)` to the Dashboard card, so the accessibility id is "Top Headlines"
        val topHeadlinesCardLocator = AppiumBy.accessibilityId("Top Headlines")
        
        val topHeadlinesCard = wait.until(ExpectedConditions.presenceOfElementLocated(topHeadlinesCardLocator))
        assertTrue("Dashboard did not load or Top Headlines card is missing.", topHeadlinesCard.isDisplayed)

        // 2. Click the Top Headlines card
        topHeadlinesCard.click()

        // 3. Wait for the Top Headlines screen to load. 
        // We can verify this by checking for the TopAppBar title "Top Headlines"
        // In Material3 Compose, TopAppBar titles usually have their text as content-desc or text attribute.
        val topHeadlinesTitleLocator = AppiumBy.xpath("//android.widget.TextView[@text='Top Headlines']")
        val titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(topHeadlinesTitleLocator))
        
        assertTrue("Did not successfully navigate to Top Headlines screen.", titleElement.isDisplayed)
    }
}

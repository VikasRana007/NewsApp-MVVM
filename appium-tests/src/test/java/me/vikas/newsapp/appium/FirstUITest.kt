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

        // 1. Locate the "Top Headlines" card on the dashboard by text
        val topHeadlinesCardLocator = AppiumBy.xpath("//android.widget.TextView[@text='Top Headlines']")

        val topHeadlinesCard = wait.until(ExpectedConditions.presenceOfElementLocated(topHeadlinesCardLocator))
        assertTrue("Dashboard did not load or Top Headlines card is missing.", topHeadlinesCard.isDisplayed)

        // 2. Click the Top Headlines card
        topHeadlinesCard.click()

        // 3. Wait for the Top Headlines screen to load (checking for loading state text or top headlines content)
        val topHeadlinesLoadingLocator = AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Fetching Top Headlines')]")
        val loadingElement = wait.until(ExpectedConditions.presenceOfElementLocated(topHeadlinesLoadingLocator))

        assertTrue("Did not successfully navigate to Top Headlines screen.", loadingElement.isDisplayed)
    }
}

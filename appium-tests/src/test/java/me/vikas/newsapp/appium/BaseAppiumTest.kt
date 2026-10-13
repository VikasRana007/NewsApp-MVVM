package me.vikas.newsapp.appium

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import org.junit.After
import org.junit.Before
import java.net.URL
import java.time.Duration

open class BaseAppiumTest {

    protected lateinit var driver: AndroidDriver

    @Before
    fun setUp() {
        val options = UiAutomator2Options()
            .setAppPackage("me.vikas.newsapp")
            .setAppActivity("me.vikas.newsapp.ui.main.MainActivity")
            .setNoReset(true)
            .setNewCommandTimeout(Duration.ofSeconds(60))

        // Robust timeouts and execution settings for local and CI devices
        options.setCapability("appium:uiautomator2ServerInstallTimeout", 120000)
        options.setCapability("appium:adbExecTimeout", 60000)
        options.setCapability("appium:androidInstallTimeout", 120000)
        
        // Give Appium more time to locate the connected device/emulator
        options.setCapability("appium:newCommandTimeout", 120)

        val serverUrl = URL("http://127.0.0.1:4723")

        driver = AndroidDriver(serverUrl, options)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
    }

    @After
    fun tearDown() {
        if (this::driver.isInitialized) {
            driver.quit()
        }
    }
}

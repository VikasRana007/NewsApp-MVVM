plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // Appium Java Client
    testImplementation("io.appium:java-client:9.2.2")
    
    // Selenium (Appium depends on it)
    testImplementation("org.seleniumhq.selenium:selenium-java:4.19.1")

    // JUnit 4
    testImplementation("junit:junit:4.13.2")
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
    }
}

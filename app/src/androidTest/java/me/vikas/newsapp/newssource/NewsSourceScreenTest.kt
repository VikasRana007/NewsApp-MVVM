package me.vikas.newsapp.newssource

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import me.vikas.newsapp.R
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.newssource.NewsSourceData
import org.junit.Rule
import org.junit.Test

class NewsSourceScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_whenUiStateIsLoading() {
        composeTestRule.setContent {
            NewsSourceData(uiState = UiState.Loading, onSourceClick = {}, onRetry = {})
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.fetching_sources)
        ).assertExists()
    }

    @Test
    fun sources_whenUIStateIsSuccess_shouldShowSuccess() {
        composeTestRule.setContent {
            NewsSourceData(uiState = UiState.Success(testSources), onSourceClick = {}, onRetry = {})
        }
        composeTestRule.onNodeWithText(
            testSources[0].name, substring = true
        ).assertExists().assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction()).performScrollToNode(
            hasText(
                testSources[2].name, substring = true
            )
        )

        composeTestRule.onNodeWithText(
            testSources[3].name, substring = true
        ).assertExists().assertHasClickAction()
    }

    @Test
    fun error_whenUiStateIsError_isErrorShown() {
        val errorMessage = "Error For UI Testing"
        composeTestRule.setContent {
            NewsSourceData(uiState = UiState.Error(errorMessage), onSourceClick = {}, onRetry = {})
        }

        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

}

private val testSources = listOf(
    Source(
        id = "al-jazeera-english",
        name = "Al Jazeera English",
        description = "News, analysis from the Middle East and worldwide",
        url = "https://www.aljazeera.com",
        category = "general",
        language = "en",
        country = "us"
    ), Source(
        id = "abc-news",
        name = "ABC NEWS",
        description = "description1",
        url = "https://description1",
        category = "general",
        language = "en",
        country = "in"
    ), Source(
        id = "bbc-sport",
        name = "BBC Sport",
        description = "description1",
        url = "http://www.bbc.co.uk/sport",
        category = "general",
        language = "en",
        country = "in"
    ), Source(
        id = "business-insider",
        name = "Business Insider",
        description = "description1",
        url = "http://www.businessinsider.com",
        category = "general",
        language = "en",
        country = "in"
    )
)
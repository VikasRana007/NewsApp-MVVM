package me.vikas.newsapp.ui.topheadline

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import me.vikas.newsapp.R
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.model.topheadline.Source
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.topheadline.TopHeadLineData
import org.junit.Rule
import org.junit.Test

class TopHeadlineScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_whenUiStateIsLoading_isShown() {
        composeTestRule.setContent {
            TopHeadLineData(
                uiState = UiState.Loading, onNewsClick = {}, onRetry = {})
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.loading)
        ).assertExists()
    }

    @Test
    fun article_whenUiStateIsSuccess_isShown() {
        composeTestRule.setContent {
            TopHeadLineData(
                uiState = UiState.Success(testArticles), onNewsClick = {}, onRetry = {})
        }

        composeTestRule.onNodeWithText(
            testArticles[0].title, substring = true
        ).assertExists().assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction()).performScrollToNode(
            hasText(
                testArticles[4].title, substring = true
            )
        )

        composeTestRule.onNodeWithText(
            testArticles[4].title, substring = true
        ).assertExists().assertHasClickAction()
    }

    @Test
    fun error_whenUiStateIsError_isErrorShown() {
        val errorMessage = "Error For UI Testing"
        composeTestRule.setContent {
            TopHeadLineData(
                uiState = UiState.Error(errorMessage),
                onNewsClick = {},
                onRetry = {}
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }
}

private val testArticles = listOf(
    Article(
        author = "Vikas Singh",
        content = "Details",
        description = "description1",
        publishedAt = "10-02-2026",
        source = Source(id = "sourceId1", name = "sourceName1"),
        title = "title1",
        url = "url1",
        urlToImage = "urlToImage1",

        ), Article(
        author = "Vikas Singh",
        content = "Details",
        description = "description2",
        publishedAt = "10-02-2026",
        source = Source(id = "sourceId2", name = "sourceName2"),
        title = "title2",
        url = "url2",
        urlToImage = "urlToImage2",
    ), Article(
        author = "Vikas Singh",
        content = "Details",
        description = "description3",
        publishedAt = "10-02-2026",
        source = Source(id = "sourceId3", name = "sourceName3"),
        title = "title3",
        url = "url3",
        urlToImage = "urlToImage3",
    ), Article(
        author = "Vikas Singh",
        content = "Details",
        description = "description4",
        publishedAt = "10-02-2026",
        source = Source(id = "sourceId4", name = "sourceName4"),
        title = "title4",
        url = "url4",
        urlToImage = "urlToImage4",
    ), Article(
        author = "Vikas Singh",
        content = "Details",
        description = "description5",
        publishedAt = "10-02-2026",
        source = Source(id = "sourceId5", name = "sourceName5"),
        title = "title5",
        url = "url5",
        urlToImage = "urlToImage5",
    )
)
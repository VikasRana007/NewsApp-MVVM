package me.vikas.newsapp.data.model.topheadline


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopHeadlinesResponse(
    @SerialName("articles")
    val articles: List<Article>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int,
    @SerialName("code")
    val code: String? = null,
    @SerialName("message")
    val message: String? = null
)
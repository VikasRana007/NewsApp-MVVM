package me.vikas.newsapp.data.model.news_source


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsSourceResponse(
    @SerialName("sources")
    val sources: MutableList<Source>,
    @SerialName("status")
    val status: String,
    @SerialName("code")
    val code: String? = null,
    @SerialName("message")
    val message: String? = null
)
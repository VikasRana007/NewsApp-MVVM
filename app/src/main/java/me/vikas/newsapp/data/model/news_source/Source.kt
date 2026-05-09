package me.vikas.newsapp.data.model.news_source


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.vikas.newsapp.data.local.entity.source.NewsSourceEntity

@Serializable
data class Source(
    @SerialName("category")
    val category: String,
    @SerialName("country")
    val country: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: String,
    @SerialName("language")
    val language: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)


fun Source.toNewsSourceEntity(): NewsSourceEntity {
    return NewsSourceEntity(
        category = category,
        country = country,
        description = description,
        id = id,
        language = language,
        name = name,
        url = url
    )
}

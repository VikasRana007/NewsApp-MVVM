package me.vikas.newsapp.data.model.topheadline


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity
import me.vikas.newsapp.data.local.entity.topheadlines.SourceEntity

@Serializable
data class Article(
    @SerialName("author")
    val author: String? = null,
    @SerialName("content")
    val content: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("publishedAt")
    val publishedAt: String,
    @SerialName("source")
    val source: Source,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("urlToImage")
    val urlToImage: String? = null
)


fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = SourceEntity(id = source.id, name = source.name),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}
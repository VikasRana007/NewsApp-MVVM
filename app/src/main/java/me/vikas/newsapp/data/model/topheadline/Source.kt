package me.vikas.newsapp.data.model.topheadline


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.vikas.newsapp.data.local.entity.topheadlines.SourceEntity

@Serializable
data class Source(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String
)

fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(
        id = id,
        name = name
    )
}
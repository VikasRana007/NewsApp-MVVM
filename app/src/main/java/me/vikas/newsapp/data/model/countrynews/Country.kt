package me.vikas.newsapp.data.model.countrynews

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String, val code: String
)

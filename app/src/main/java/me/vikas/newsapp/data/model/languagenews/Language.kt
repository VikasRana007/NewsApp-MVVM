package me.vikas.newsapp.data.model.languagenews

import kotlinx.serialization.Serializable

@Serializable
data class Language(val languageName: String, val languageCode: String)

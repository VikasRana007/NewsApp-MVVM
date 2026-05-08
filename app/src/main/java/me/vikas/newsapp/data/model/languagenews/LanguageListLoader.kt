package me.vikas.newsapp.data.model.languagenews

import android.content.Context
import kotlinx.serialization.json.Json

object LanguageListLoader {
    private var cachedLanguage: List<Language>? = null

    /**
     * Due to this cache system File IO
     * operation happens only once.
     */

    fun loadLanguage(context: Context): List<Language> {
        cachedLanguage?.let { return it }
        val jsonString = context.assets.open("language.json").bufferedReader().use {
            it.readText()
        }
        val language: List<Language> = Json.decodeFromString(jsonString)
        cachedLanguage = language
        return language
    }
}
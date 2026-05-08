package me.vikas.newsapp.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.data.model.languagenews.LanguageListLoader
import javax.inject.Inject

class LanguageLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadLanguage(): List<Language> {
        return LanguageListLoader.loadLanguage(context)
    }
}
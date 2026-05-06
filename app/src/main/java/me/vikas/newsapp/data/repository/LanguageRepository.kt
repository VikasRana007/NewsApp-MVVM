package me.vikas.newsapp.data.repository

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.vikas.newsapp.data.local.LanguageLocalDataSource
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.utils.AppConstant.NO_LANGUAGE_FOUND
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    private val languageLocalDataSource: LanguageLocalDataSource
) {

    fun getLanguage(): Flow<List<Language>> = flow {
        val languageList = languageLocalDataSource.loadLanguage()
        if (languageList.isNotEmpty()) {
            emit(languageList)
        } else {
            throw Exception(NO_LANGUAGE_FOUND)
        }
    }

}
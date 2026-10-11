package me.vikas.newsapp.domain.usecase.language

import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.data.repository.LanguageRepository
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): Flow<List<Language>> {
        return languageRepository.getLanguage()
    }
}

package me.vikas.newsapp.domain.usecase.country

import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.data.repository.CountryRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {
    operator fun invoke(): Flow<List<Country>> {
        return countryRepository.getCountries()
    }
}

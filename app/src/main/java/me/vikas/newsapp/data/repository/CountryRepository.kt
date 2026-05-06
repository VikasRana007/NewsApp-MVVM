package me.vikas.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.vikas.newsapp.data.local.CountryLocalDataSource
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.utils.AppConstant.NO_COUNTRY_FOUND
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val localDataSource: CountryLocalDataSource
) {

    fun getCountries(): Flow<List<Country>> = flow {
        val countryList = localDataSource.loadCountries()
        if (countryList.isNotEmpty()) {
            emit(countryList)
        } else {
            throw Exception(NO_COUNTRY_FOUND)
        }
    }
}
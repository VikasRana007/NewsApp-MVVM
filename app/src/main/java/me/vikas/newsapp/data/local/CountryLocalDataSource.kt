package me.vikas.newsapp.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.data.model.countrynews.CountryListLoader.loadCountries
import javax.inject.Inject

class CountryLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadCountries(): List<Country> {
        return loadCountries(context)
    }
}
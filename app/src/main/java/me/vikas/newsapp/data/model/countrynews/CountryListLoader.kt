package me.vikas.newsapp.data.model.countrynews

import android.content.Context
import kotlinx.serialization.json.Json

object CountryListLoader {
    private var cachedCountries: List<Country>? = null

    /**
     * Due to this cache system File IO
     * operation happens only once.
     */

    fun loadCountries(context: Context): List<Country> {
        cachedCountries?.let { return it }
        val jsonString = context.assets.open("countries.json").bufferedReader().use {
            it.readText()
        }
        val countries: List<Country> = Json.decodeFromString(jsonString)
        cachedCountries = countries
        return countries
    }
}
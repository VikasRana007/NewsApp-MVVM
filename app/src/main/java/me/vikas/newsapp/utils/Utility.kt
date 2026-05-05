package me.vikas.newsapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import kotlinx.serialization.json.Json
import me.vikas.newsapp.R
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.data.model.topheadline.Article

// Type Alias for Click Listener in Kotlin Way
typealias ArticleClick = (Article) -> Unit
typealias SourceClick = (Source) -> Unit
typealias NewsCategoryClick = (Article) -> Unit
typealias CountrySelect = (Country) -> Unit
typealias LanguageSelect = (Language) -> Unit
typealias SearchedItem = (Article) -> Unit


// Image Loading Extension function using coil in Kotlin Way
fun AppCompatImageView.loadImage(url: String?) {
    this.load(url) {
        crossfade(true)
        placeholder(R.drawable.android)
        error(R.drawable.error_warning)
    }
}


// To switching the activity Extension in kotlin Way
inline fun <reified T : Activity> Context.launchActivity(isFinish: Boolean = false) {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    if (isFinish && this is Activity) {
        finish()
    }
}

fun displayToast(context: Context, msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
    toast.show()
}
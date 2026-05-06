package me.vikas.newsapp.ui.languagewisenews

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.vikas.newsapp.R
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.databinding.ActivityLanguageWiseNewsScreenBinding
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.newssource.NewsSourceActivity
import me.vikas.newsapp.utils.AppConstant.LANGUAGE
import me.vikas.newsapp.utils.displayToast
import me.vikas.newsapp.utils.launchActivity
import javax.inject.Inject
@AndroidEntryPoint
class LanguageWiseNewsScreen : AppCompatActivity() {

    private val languageWiseViewModel: LanguageWiseViewModel by viewModels()

    @Inject
    lateinit var languageWiseAdapter: LanguageWiseAdapter

    private lateinit var activityLanguageWiseNewsScreenBinding: ActivityLanguageWiseNewsScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLanguageWiseNewsScreenBinding =
            ActivityLanguageWiseNewsScreenBinding.inflate(layoutInflater)
        setContentView(activityLanguageWiseNewsScreenBinding.root)


        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        try {
            setSupportActionBar(activityLanguageWiseNewsScreenBinding.toolbar)
            languageWiseAdapter.onLanguageSelect {
                languageSelect(it)
            }
            activityLanguageWiseNewsScreenBinding.apply {
                supportActionBar?.title = getString(R.string.language)
                languageRcv.layoutManager = LinearLayoutManager(this@LanguageWiseNewsScreen)
                languageRcv.adapter = languageWiseAdapter
            }
        } catch (ex: Exception) {
            println("Exception in setupUI of Country List : ${ex.message}")
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languageWiseViewModel.uiState.collect { uiState ->

                    when (uiState) {
                        is UiState.Success -> {
                            activityLanguageWiseNewsScreenBinding.progressBar.visibility = View.GONE
                            languageWiseAdapter.submitList(uiState.data)
                            activityLanguageWiseNewsScreenBinding.languageRcv.visibility =
                                View.VISIBLE
                        }

                        is UiState.Error -> {
                            activityLanguageWiseNewsScreenBinding.apply {
                                progressBar.visibility = View.GONE
                                displayToast(
                                    this@LanguageWiseNewsScreen,
                                    "Something Went Wrong," + " Try again"
                                )
                            }
                        }

                        is UiState.Loading -> {
                            activityLanguageWiseNewsScreenBinding.apply {
                                progressBar.visibility = View.VISIBLE
                                languageRcv.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    fun languageSelect(language: Language) {
        LANGUAGE = language.languageCode
        launchActivity<NewsSourceActivity>(false)
    }
}
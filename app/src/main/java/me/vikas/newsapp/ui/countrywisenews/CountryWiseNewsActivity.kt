package me.vikas.newsapp.ui.countrywisenews

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
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.databinding.ActivityContryWiseNewsBinding
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.topheadline.TopHeadlineActivity
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import me.vikas.newsapp.utils.displayToast
import me.vikas.newsapp.utils.launchActivity
import javax.inject.Inject

@AndroidEntryPoint
class CountryWiseNewsActivity : AppCompatActivity() {
    private val countryWiseNewsViewModel: CountryWiseNewsViewModel by viewModels()

    @Inject
    lateinit var countryWiseNewsAdapter: CountryWiseNewsAdapter

    private lateinit var binding: ActivityContryWiseNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContryWiseNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupObserver()
    }


    private fun setupUI() {
        try {
            setSupportActionBar(binding.toolbar)
            countryWiseNewsAdapter.onCountrySelect {
                countryName(it)
            }
            binding.apply {
                supportActionBar?.title = getString(R.string.county_name)
                countryRcv.layoutManager = LinearLayoutManager(this@CountryWiseNewsActivity)
                countryRcv.adapter = countryWiseNewsAdapter
            }
        } catch (ex: Exception) {
            println("Exception in setupUI of Country List : ${ex.message}")
        }
    }


    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryWiseNewsViewModel.uiState.collect { uiState ->

                    when (uiState) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            countryWiseNewsAdapter.submitList(uiState.data)
                            binding.countryRcv.visibility = View.VISIBLE
                        }

                        is UiState.Error -> {
                            binding.apply {
                                progressBar.visibility = View.GONE
                                displayToast(
                                    this@CountryWiseNewsActivity,
                                    "Something Went Wrong," + " Try again"
                                )
                            }
                        }

                        is UiState.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                countryRcv.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

    }


    fun countryName(country: Country) {
        COUNTRY = country.code
        launchActivity<TopHeadlineActivity>(false)
    }

}
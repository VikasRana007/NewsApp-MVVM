package me.vikas.newsapp.ui.searchnews

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.vikas.newsapp.R
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.databinding.ActivityNewsSearchBinding
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.TAG
import javax.inject.Inject

@AndroidEntryPoint
class NewsSearchActivity : AppCompatActivity() {

    private val newsSearchViewModel: NewsSearchViewModel by viewModels()

    @Inject
    lateinit var newsSearchAdapter: NewsSearchAdapter

    private lateinit var activityNewsSearchBinding: ActivityNewsSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsSearchBinding = ActivityNewsSearchBinding.inflate(layoutInflater)
        setContentView(activityNewsSearchBinding.root)
        setUpUI()
        setupObserver()
    }


    private fun setUpUI() {
        try {
            activityNewsSearchBinding.apply {
                supportActionBar?.title = getString(R.string.search_news)
                newsSearchAdapter.searchItemListener {
                    openArticle(it)
                }
                newsRecyclerView.layoutManager = LinearLayoutManager(this@NewsSearchActivity)
                newsRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        newsRecyclerView.context,
                        (newsRecyclerView.layoutManager as LinearLayoutManager).orientation
                    )
                )
                newsRecyclerView.adapter = newsSearchAdapter


                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        newsSearchViewModel.searchNews(newText)
                        return true
                    }
                })
            }

        } catch (ex: Exception) {
            ex.message?.let { Log.d(TAG, it) }
        }
        setSupportActionBar(activityNewsSearchBinding.toolbar)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSearchViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            activityNewsSearchBinding.progressBar.visibility = View.GONE
                            newsSearchAdapter.submitList(it.data)
                            activityNewsSearchBinding.newsRecyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            activityNewsSearchBinding.apply {
                                progressBar.visibility = View.VISIBLE
                                newsRecyclerView.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            activityNewsSearchBinding.apply {
                                progressBar.visibility = View.GONE

                                println("Error in Parsing the Data: ${it.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    fun openArticle(article: Article) {
        newsSearchViewModel.openUrl(article.url, this@NewsSearchActivity)
    }

}
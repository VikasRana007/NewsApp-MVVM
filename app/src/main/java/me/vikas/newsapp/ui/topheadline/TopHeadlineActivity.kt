package me.vikas.newsapp.ui.topheadline

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.vikas.newsapp.R
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.databinding.ActivityTopHeadlineBinding
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.TAG
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlineActivity : AppCompatActivity() {

    private val topHeadlineViewModel: TopHeadlineViewModel by viewModels()

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter
    private lateinit var activityTopHeadlineBinding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTopHeadlineBinding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(activityTopHeadlineBinding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        try {
            setSupportActionBar(activityTopHeadlineBinding.toolbar)
            topHeadlineAdapter.topHeadlinesListener {
                openArticle(it)
            }
            activityTopHeadlineBinding.apply {
                supportActionBar?.title = getString(R.string.top_headlines)
                topHeadlineRecyclerView.layoutManager =
                    LinearLayoutManager(this@TopHeadlineActivity)
                topHeadlineRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        topHeadlineRecyclerView.context,
                        (topHeadlineRecyclerView.layoutManager as LinearLayoutManager).orientation
                    )
                )
                topHeadlineRecyclerView.adapter = topHeadlineAdapter
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.d(TAG, it) }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlineViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            activityTopHeadlineBinding.progressBar.visibility = View.GONE
                            topHeadlineAdapter.submitList(it.data)
                            activityTopHeadlineBinding.topHeadlineRecyclerView.visibility =
                                View.VISIBLE
                        }

                        is UiState.Loading -> {
                            activityTopHeadlineBinding.apply {
                                progressBar.visibility = View.VISIBLE
                                topHeadlineRecyclerView.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            activityTopHeadlineBinding.apply {
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
        topHeadlineViewModel.openUrl(article.url, this@TopHeadlineActivity)
    }
}
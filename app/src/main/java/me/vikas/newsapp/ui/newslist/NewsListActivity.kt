package me.vikas.newsapp.ui.newslist

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
import me.vikas.newsapp.databinding.ActivityNewsListBinding
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.SOURCE
import me.vikas.newsapp.utils.AppConstant.TAG
import me.vikas.newsapp.utils.displayToast
import javax.inject.Inject
@AndroidEntryPoint
class NewsListActivity : AppCompatActivity() {

    private val newsListViewModel: NewsListViewModel by viewModels()

    @Inject
    lateinit var newsListAdapter: NewsListAdapter

    private lateinit var activityNewsListBinding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsListBinding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(activityNewsListBinding.root)

        setUpUI()
        newsListViewModel.fetchNewsCategoryList(SOURCE)
        setupObserver()
    }

    private fun setUpUI() {
        try {
            setSupportActionBar(activityNewsListBinding.toolbar)
            newsListAdapter.newsCategoryListener {
                openArticle(it)
            }
            activityNewsListBinding.apply {
                supportActionBar?.title = getString(R.string.top_headlines)
                newsListRecyclerView.layoutManager = LinearLayoutManager(this@NewsListActivity)
                newsListRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        newsListRecyclerView.context,
                        (newsListRecyclerView.layoutManager as LinearLayoutManager).orientation
                    )
                )
                newsListRecyclerView.adapter = newsListAdapter
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.d(TAG, it) }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            activityNewsListBinding.progressBar.visibility = View.GONE
                            newsListAdapter.submitList(it.data)
                            activityNewsListBinding.newsListRecyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            activityNewsListBinding.apply {
                                progressBar.visibility = View.VISIBLE
                                newsListRecyclerView.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            activityNewsListBinding.apply {
                                progressBar.visibility = View.GONE
                                displayToast(this@NewsListActivity, it.message)
                            }
                        }
                    }
                }
            }
        }
    }


    fun openArticle(article: Article) {
        newsListViewModel.openUrl(article.url, this@NewsListActivity)
    }

}
package me.vikas.newsapp.ui.newssource

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.vikas.newsapp.NewsApplication
import me.vikas.newsapp.R
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.databinding.ActivityNewsSourceBinding
import me.vikas.newsapp.di.component.DaggerActivityComponent
import me.vikas.newsapp.di.module.ActivityModule
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.TAG
import javax.inject.Inject

class NewsSourceActivity : AppCompatActivity() {

    @Inject
    lateinit var newsSourceViewModel: NewsSourceViewModel

    @Inject
    lateinit var newsSourceAdapter: NewsSourceAdapter

    private lateinit var activityNewsSourceBinding: ActivityNewsSourceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        activityNewsSourceBinding = ActivityNewsSourceBinding.inflate(layoutInflater)
        setContentView(activityNewsSourceBinding.root)
        setUpUI()
        setupObserver()
    }


    private fun setUpUI() {
        try {
            setSupportActionBar(activityNewsSourceBinding.toolbar)

            activityNewsSourceBinding.apply {
                supportActionBar?.title = getString(R.string.news_sources)
                newsSourceRecyclerView.layoutManager = LinearLayoutManager(this@NewsSourceActivity)
                newsSourceRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        newsSourceRecyclerView.context,
                        (newsSourceRecyclerView.layoutManager as LinearLayoutManager).orientation
                    )
                )
                newsSourceRecyclerView.adapter = newsSourceAdapter
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.d(TAG, it) }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourceViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            activityNewsSourceBinding.progressBar.visibility = View.GONE
                            newsSourceAdapter.submitList(it.data.toMutableList())
                            activityNewsSourceBinding.newsSourceRecyclerView.visibility =
                                View.VISIBLE
                        }

                        is UiState.Loading -> {
                            activityNewsSourceBinding.apply {
                                progressBar.visibility = View.VISIBLE
                                newsSourceRecyclerView.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            activityNewsSourceBinding.apply {
                                progressBar.visibility = View.GONE

                                println("Error in Parsing the Data: ${it.message}")
                            }
                        }
                    }
                }
            }
        }
    }


    fun openArticle(source: Source) {
       // newsSourceViewModel.openUrl(article.url, this@NewsSourceActivity)
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}
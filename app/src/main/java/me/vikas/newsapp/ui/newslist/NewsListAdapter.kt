package me.vikas.newsapp.ui.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.databinding.TopHeadlinesItemLayoutBinding
import me.vikas.newsapp.utils.ArticleDiffUtilHelper
import me.vikas.newsapp.utils.NewsCategoryClick
import me.vikas.newsapp.utils.loadImage

class NewsListAdapter(
    private val articles: MutableList<Article>
) : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    private var onNewsCategoryClick: NewsCategoryClick? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsListViewHolder {
        val binding = TopHeadlinesItemLayoutBinding.inflate(
            LayoutInflater.from(
                viewGroup.context
            ), viewGroup, false
        )
        return NewsListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: NewsListViewHolder, position: Int) {
        viewHolder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun newsCategoryListener(onNewsCategoryClick: NewsCategoryClick) {
        this.onNewsCategoryClick = onNewsCategoryClick
    }

    fun submitList(newListOfArticles: List<Article>) {
        val diffCallback = ArticleDiffUtilHelper(articles, newListOfArticles)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        articles.clear()
        articles.addAll(newListOfArticles)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NewsListViewHolder(private val binding: TopHeadlinesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                title.text = article.title
                description.text = article.description
                source.text = article.source.name
                newsImage.loadImage(article.urlToImage)
                root.setOnClickListener {
                    onNewsCategoryClick?.invoke(article)
                }
            }
        }
    }
}
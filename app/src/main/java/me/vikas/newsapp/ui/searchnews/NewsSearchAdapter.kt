package me.vikas.newsapp.ui.searchnews

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.databinding.TopHeadlinesItemLayoutBinding
import me.vikas.newsapp.utils.ArticleDiffUtilHelper
import me.vikas.newsapp.utils.SearchedItem
import me.vikas.newsapp.utils.loadImage

class NewsSearchAdapter(private val articles: MutableList<Article>
) : RecyclerView.Adapter<NewsSearchAdapter.ArticleViewHolder>() {

    private var onSearchItemClick: SearchedItem? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = TopHeadlinesItemLayoutBinding.inflate(
            LayoutInflater.from(
                viewGroup.context
            ), viewGroup, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ArticleViewHolder, position: Int) {
        viewHolder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun submitList(newListOfArticles: List<Article>) {
        val diffCallback = ArticleDiffUtilHelper(articles, newListOfArticles)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        articles.clear()
        articles.addAll(newListOfArticles)
        diffResult.dispatchUpdatesTo(this)
    }

    fun searchItemListener(onSearchItemClick: SearchedItem) {
        this.onSearchItemClick = onSearchItemClick
    }

    inner class ArticleViewHolder(private val binding: TopHeadlinesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                title.text = article.title
                description.text = article.description
                source.text = article.source.name
                Log.d("IMAGE_URL", article.urlToImage ?: "NULL")
                newsImage.loadImage(article.urlToImage)
                root.setOnClickListener {
                    onSearchItemClick?.invoke(article)
                }
            }
        }
    }
}
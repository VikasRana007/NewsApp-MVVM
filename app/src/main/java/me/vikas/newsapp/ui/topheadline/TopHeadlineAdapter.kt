package me.vikas.newsapp.ui.topheadline

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.databinding.TopHeadlinesItemLayoutBinding
import me.vikas.newsapp.utils.ArticleClick
import me.vikas.newsapp.utils.ArticleDiffUtilHelper
import me.vikas.newsapp.utils.loadImage

class TopHeadlineAdapter(
    private val articles: MutableList<Article>
) : RecyclerView.Adapter<TopHeadlineAdapter.ArticleViewHolder>() {

    private var onArticleClick: ArticleClick? = null

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

    fun topHeadlinesListener(onArticleClick: ArticleClick) {
        this.onArticleClick = onArticleClick
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
                    onArticleClick?.invoke(article)
                }
            }
        }
    }
}
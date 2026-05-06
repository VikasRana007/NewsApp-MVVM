package me.vikas.newsapp.ui.newssource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.databinding.NewsSourceItemLayoutBinding
import me.vikas.newsapp.utils.SourceClick

class NewsSourceAdapter(
    private val newsSource: MutableList<Source>
) : RecyclerView.Adapter<NewsSourceAdapter.SourceViewHolder>() {

    private var newsSourceClick: SourceClick? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SourceViewHolder {
        val binding = NewsSourceItemLayoutBinding.inflate(
            LayoutInflater.from(
                viewGroup.context
            ), viewGroup, false
        )
        return SourceViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SourceViewHolder, position: Int) {
        viewHolder.bind(newsSource[position])
    }

    override fun getItemCount(): Int {
        return newsSource.size
    }

    fun submitList(listOfSource: MutableList<Source>) {
        newsSource.addAll(listOfSource)
    }

    fun onSourceClick(newsSourceClick: SourceClick) {
        this.newsSourceClick = newsSourceClick
    }

    inner class SourceViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: Source) {
            binding.apply {
                newsSource.text = source.name
                root.setOnClickListener {
                    @Suppress("DEPRECATION") if (position != RecyclerView.NO_POSITION) {
                        newsSourceClick?.invoke(source)
                    }
                }
            }
        }
    }

}
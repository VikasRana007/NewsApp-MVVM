package me.vikas.newsapp.ui.newssource

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.databinding.NewsSourceItemLayoutBinding

class NewsSourceAdapter(
    private val onSourceClick: (Source) -> Unit, private val newsSource: MutableList<Source>
) : RecyclerView.Adapter<NewsSourceAdapter.SourceViewHolder>() {

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

    inner class SourceViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: Source) {
            binding.apply {
                newsSource.text = source.name
                Log.d("Source Name :", source.name)

                root.setOnClickListener {
                    //if (position != RecyclerView.NO_POSITION) {
                    onSourceClick(source)
                    //}
                }
            }
        }
    }

}
package me.vikas.newsapp.ui.languagewisenews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.databinding.LanguageItemLayoutBinding
import me.vikas.newsapp.utils.LanguageSelect

class LanguageWiseAdapter(
    private val language: MutableList<Language>
) : RecyclerView.Adapter<LanguageWiseAdapter.LanguageWiseNewsViewHolder>() {

    private var languageWiseNewsClick: LanguageSelect? = null

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, position: Int
    ): LanguageWiseNewsViewHolder {

        val binding = LanguageItemLayoutBinding.inflate(
            LayoutInflater.from(
                viewGroup.context
            ), viewGroup, false
        )
        return LanguageWiseNewsViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: LanguageWiseNewsViewHolder, position: Int) {
        viewHolder.bind(language[position])
    }

    override fun getItemCount(): Int {
        return language.size
    }

    fun submitList(newListOfLanguage: List<Language>) {
        language.addAll(newListOfLanguage)
    }

    fun onLanguageSelect(languageWiseNewsClick: LanguageSelect) {
        this.languageWiseNewsClick = languageWiseNewsClick
    }

    inner class LanguageWiseNewsViewHolder(private val binding: LanguageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(language: Language) {
            binding.apply {
                languageName.text = language.languageName
                languageCode.text = language.languageCode
                root.setOnClickListener {
                    languageWiseNewsClick?.invoke(language)
                }
            }
        }
    }
}
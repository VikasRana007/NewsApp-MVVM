package me.vikas.newsapp.utils

import androidx.recyclerview.widget.DiffUtil
import me.vikas.newsapp.data.model.topheadline.Article

class ArticleDiffUtilHelper(
    private val oldList: List<Article>,
    private val newList: List<Article>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].url ==
                newList[newItemPosition].url
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] ==
                newList[newItemPosition]
    }
}
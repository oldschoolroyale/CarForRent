package com.kaisho.carforrent.diffUtills

import androidx.recyclerview.widget.DiffUtil
import com.kaisho.carforrent.model.FavoritePOJO

class FavoriteDiffUtil(
    private val oldList: List<FavoritePOJO>,
    private val newList: List<FavoritePOJO>
):DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].image == newList[newItemPosition].image
                && oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].price == newList[newItemPosition].price
    }
}
package com.kaisho.carforrent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.carforrent.databinding.FavoriteCarItemBinding
import com.kaisho.carforrent.diffUtills.FavoriteDiffUtil
import com.kaisho.carforrent.model.FavoritePOJO
import com.squareup.picasso.Picasso

class FavoriteAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var favoriteList = emptyList<FavoritePOJO>()

    fun setData(newFavoriteList: List<FavoritePOJO>){
        val diffUtil = FavoriteDiffUtil(favoriteList, newFavoriteList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        this.favoriteList = newFavoriteList
        diffUtilResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favoriteList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoriteViewHolder){
            holder.bind(favoriteList[position])
        }
    }

    class FavoriteViewHolder(private val binding: FavoriteCarItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(favoritePOJO: FavoritePOJO){
            binding.favoritePOJO = favoritePOJO
            favoritePOJO.image.let { url ->
                Picasso.with(itemView.context).load(url).into(binding.favoriteCarItemImage)
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): FavoriteViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteCarItemBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(binding)
            }
        }

    }
}
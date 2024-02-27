package com.juan.superheroapp_bbdd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juan.superheroapp_bbdd.data.SuperheroItemResponse
import com.juan.superheroapp_bbdd.data.database.entities.HeroEntity

class SuperheroAdapter(var superheroList: List<HeroEntity> = emptyList(), private val navigateToDetailActivity: (String) -> Unit) : RecyclerView.Adapter<SuperheroViewHolder>() {

    fun updateList(list: List<HeroEntity>) {
        superheroList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        return SuperheroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        )
    }
    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        holder.bind(superheroList[position],navigateToDetailActivity)
    }
    override fun getItemCount() = superheroList.size
}

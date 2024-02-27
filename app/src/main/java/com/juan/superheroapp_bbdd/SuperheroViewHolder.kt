package com.juan.superheroapp_bbdd

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.juan.superheroapp_bbdd.data.database.entities.HeroEntity
import com.juan.superheroapp_bbdd.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)
    fun bind(superheroItem: HeroEntity, navigateToDetailActivity: (String) -> Unit) {
        binding.tvSuperheroName.text = superheroItem.name
        Picasso.get().load(superheroItem.image).into(binding.ivSuperhero)
        binding.root.setOnClickListener {
            navigateToDetailActivity(superheroItem.id.toString())
        }
    }
}

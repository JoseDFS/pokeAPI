package com.example.pokemovie.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pokemovie.MyPokemonAdapter
import com.example.pokemovie.R
import com.example.pokemovie.pojos.Pokemon
import kotlinx.android.synthetic.main.cardview_pokemon.view.*

class PokemonAdapter(var pokemons: List<Pokemon>, val clickListener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonAdapter.ViewHolder>(),
    MyPokemonAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_pokemon, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemons[position], clickListener)
    }

    override fun changeDataSet(newDataSet: List<Pokemon>) {
        this.pokemons = newDataSet

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item:Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView){
            var id =item.id
            Glide.with(itemView.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
                .placeholder(R.drawable.ic_launcher_background)
                .into(pokemon_image_cv)

            pokemon_title_cv.text = item.name
            pokemon_id_cv.text = pokemon_id_cv.text.toString() + " " + item.id
            pokemon_height_cv.text =  pokemon_height_cv.text.toString() + " " + item.height
            pokemon_weight_cv.text = pokemon_weight_cv.text.toString() + " " + item.weight

            this.setOnClickListener { clickListener(item) }
        }
    }
}
package com.example.pokemovie.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemovie.MyPokemonAdapter
import com.example.pokemovie.R
import com.example.pokemovie.pojos.Pokemon
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonSimpleListAdapter(var pokemons:List<Pokemon>, val clickListener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonSimpleListAdapter.ViewHolder>(),
    MyPokemonAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) =holder.bind(pokemons[pos], clickListener)

    override fun changeDataSet(newDataSet: List<Pokemon>) {
        this.pokemons = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(pokemon: Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView){
            name_list_item.text = pokemon.name
            id_list_item.text=pokemon.id

            this.setOnClickListener { clickListener(pokemon) }
        }
    }
}
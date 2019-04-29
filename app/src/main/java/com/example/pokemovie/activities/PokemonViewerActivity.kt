package com.example.pokemovie.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.pokemovie.R
import com.example.pokemovie.pojos.Pokemon
import kotlinx.android.synthetic.main.activity_pokemon_viewer.*

class PokemonViewerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_viewer)

        setSupportActionBar(toolbarviewer)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        val reciever: Pokemon = intent?.extras?.getParcelable("POKEMON") ?: Pokemon()
        init(reciever)
    }

    fun init(pokemon: Pokemon){
        var id = pokemon.id
        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .placeholder(R.drawable.ic_launcher_background)
            .into(app_bar_image_viewer)
        collapsingtoolbarviewer.title = pokemon.name
        app_bar_id_viewer.text = pokemon.id
        height_viewer.text = pokemon.height
        weight_viewer.text = pokemon.weight

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}

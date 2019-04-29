package com.example.pokemovie


import com.example.pokemovie.pojos.Pokemon
import java.util.*

object AppConstants{
    val dataset_saveinstance_key = "CLE"
    val MAIN_LIST_KEY = "key_list_movies"
}

interface MyPokemonAdapter {
    fun changeDataSet(newDataSet : List<Pokemon>)
}
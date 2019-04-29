package com.example.pokemovie.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemovie.AppConstants
import com.example.pokemovie.MyPokemonAdapter

import com.example.pokemovie.R
import com.example.pokemovie.adapters.PokemonAdapter
import com.example.pokemovie.adapters.PokemonSimpleListAdapter
import com.example.pokemovie.pojos.Pokemon
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var  pokemons :ArrayList<Pokemon>
    private lateinit var pokemonsAdapter : MyPokemonAdapter
    var listenerTool :  SearchNewPokemonListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)

        if(savedInstanceState != null) pokemons = savedInstanceState.getParcelableArrayList<Pokemon>(AppConstants.MAIN_LIST_KEY)!!

        initRecyclerView(resources.configuration.orientation, view)
        initSearchButton(view)

        return view
    }

    fun initRecyclerView(orientation:Int, container:View){
        val linearLayoutManager = LinearLayoutManager(this.context)

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            pokemonsAdapter = PokemonAdapter(pokemons, {pokemon:Pokemon->listenerTool?.managePortraitItemClick(pokemon)})
            container.pokemon_list_rv.adapter = pokemonsAdapter as PokemonAdapter
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            pokemonsAdapter = PokemonSimpleListAdapter(pokemons, { pokemon:Pokemon->listenerTool?.manageLandscapeItemClick(pokemon)})
            container.pokemon_list_rv.adapter = pokemonsAdapter as PokemonSimpleListAdapter
        }

        container.pokemon_list_rv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    fun initSearchButton(container:View) = container.add_movie_btn.setOnClickListener {
        listenerTool?.searchPokemon(pokemon_name_et.text.toString())
    }

    fun updatePokemonsAdapter(pokemonList: ArrayList<Pokemon>){ pokemonsAdapter.changeDataSet(pokemonList) }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchNewPokemonListener) {
            listenerTool = context
        } else {
            throw RuntimeException("Se necesita una implementación de  la interfaz")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerTool = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.MAIN_LIST_KEY,pokemons)
        super.onSaveInstanceState(outState)
    }

    interface SearchNewPokemonListener{
        fun searchPokemon(pokemonName: String)

        fun managePortraitItemClick(pokemon: Pokemon)

        fun manageLandscapeItemClick(pokemon: Pokemon)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainListFragment.
         */
        fun newInstance(dataset : ArrayList<Pokemon>): MainListFragment{
            val newFragment = MainListFragment()
            newFragment.pokemons = dataset
            return newFragment
        }


    }
}

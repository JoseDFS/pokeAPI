package com.example.pokemovie.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.pokemovie.R
import com.example.pokemovie.pojos.Pokemon
import kotlinx.android.synthetic.main.cardview_pokemon.view.*
import kotlinx.android.synthetic.main.fragment_main_content.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainContentFragment : Fragment() {
    var pokemon = Pokemon()

    companion object {
        fun newInstance(pokemon: Pokemon): MainContentFragment{
            val newFragment = MainContentFragment()
            newFragment.pokemon = pokemon
            return newFragment
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main_content, container, false)

        bindData(view)

        return view
    }

    fun bindData(view: View){
        var id =pokemon.id
        view.id_main_content_fragment.text = pokemon.id
        view.pokemon_name_main_content_fragment.text = pokemon.name
        view.height_main_content_fragment.text = pokemon.height
        view.weight_main_content_fragment.text = pokemon.weight
        //view.types_main_content_fragment.text = pokemon.types
        Glide.with(view)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.image_main_content_fragment)
    }
}

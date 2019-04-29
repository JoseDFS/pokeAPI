package com.example.pokemovie.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.example.pokemovie.AppConstants
import com.example.pokemovie.R
import com.example.pokemovie.fragments.MainContentFragment
import com.example.pokemovie.fragments.MainListFragment
import com.example.pokemovie.network.NetworkUtils
import com.example.pokemovie.pojos.Pokemon
import com.google.gson.Gson
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), MainListFragment.SearchNewPokemonListener {
    private lateinit var mainFragment: MainListFragment
    private lateinit var mainContentFragment: MainContentFragment

    private var pokemonList = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pokemonList = savedInstanceState?.getParcelableArrayList(AppConstants.dataset_saveinstance_key) ?: ArrayList()

        initMainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.dataset_saveinstance_key, pokemonList)
        super.onSaveInstanceState(outState)
    }

    fun initMainFragment() {
        mainFragment = MainListFragment.newInstance(pokemonList)

        val resource = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            R.id.main_fragment
        else {
            mainContentFragment = MainContentFragment.newInstance(Pokemon())
            changeFragment(R.id.land_main_cont_fragment, mainContentFragment)

            R.id.land_main_fragment
        }

        changeFragment(resource, mainFragment)
    }

    fun addPokemonToList(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        mainFragment.updatePokemonsAdapter(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }

    override fun searchPokemon(movieName: String) {
        FetchMovie().execute(movieName)
    }

    override fun managePortraitItemClick(pokemon: Pokemon) {
        val pokeBundle = Bundle()
        pokeBundle.putParcelable("POKEMON", pokemon)
        startActivity(Intent(this, PokemonViewerActivity::class.java).putExtras(pokeBundle))
    }

    private fun changeFragment(id: Int, frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, frag).commit()
    }

    override fun manageLandscapeItemClick(pokemon: Pokemon) {
        mainContentFragment = MainContentFragment.newInstance(pokemon)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    private inner class FetchMovie : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {

            if (params.isNullOrEmpty()) return ""

            val ID = params[0]
            val pokeUrl = NetworkUtils().buildUrl("pokemon", ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeUrl)
            } catch (e: IOException) {
                ""
            }
        }

        override fun onPostExecute(pokeInfo: String) {
            super.onPostExecute(pokeInfo)
            if (!pokeInfo.isEmpty()) {
                val pokemon = Gson().fromJson<Pokemon>(pokeInfo, Pokemon::class.java)
                addPokemonToList(pokemon)


            } else {
                Toast.makeText(this@MainActivity, "A ocurrido un error,", Toast.LENGTH_LONG).show()
            }
        }
    }
}


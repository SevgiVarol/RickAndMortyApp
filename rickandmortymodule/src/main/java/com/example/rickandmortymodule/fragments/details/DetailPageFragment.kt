package com.example.rickandmortymodule.fragments.details

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortymodule.R
import com.example.rickandmortymodule.RickAndMortyModule
import com.example.rickandmortymodule.databinding.FragmentDetailsBinding
import com.example.rickandmortymodule.models.CharacterModel
import com.squareup.picasso.Picasso

class DetailPageFragment : Fragment(){
    private lateinit var viewModel: DetailsViewModel
    var characterId: Int? = null
    var name: TextView? = null
    var speciesStatus: TextView? = null
    var location: TextView? = null
    var gender: TextView? = null
    var image: ImageView? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        characterId = arguments?.getInt("id")
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        return FragmentDetailsBinding.inflate(inflater, container, false).also {
            val toolbar = it.toolbar
            name = it.name
            speciesStatus = it.speciesStatus
            location = it.location
            gender = it.gender
            image = it.image
            activity?.setActionBar(toolbar)
            activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
            activity?.actionBar?.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            characterId?.let {id->
                setVariables(this,id).execute()
            }?:kotlin.run {
                activity?.onBackPressed()
            }
        }.root
    }

    class setVariables(
        fragment: DetailPageFragment,
        characterId: Int
    ) : AsyncTask<String?, CharacterModel?, CharacterModel?>() {
        var fragment = fragment
        var characterId = characterId
        var character: CharacterModel? = null
        override fun doInBackground(vararg p0: String?): CharacterModel? {
            character = RickAndMortyModule.getSelectedCharacterDetails(characterId)
            return character
        }

        override fun onPostExecute(result: CharacterModel?) {
            result?.let {
                fragment.name?.setText(it.name)
                fragment.speciesStatus?.setText(it.species+" - "+it.getStatus())
                fragment.location?.setText(it.lastKnownLocation?.name)
                fragment.gender?.setText(it.getGender().name)
                Picasso.with(fragment.context).load(it.image).into(fragment.image)
            }
        }
    }
}
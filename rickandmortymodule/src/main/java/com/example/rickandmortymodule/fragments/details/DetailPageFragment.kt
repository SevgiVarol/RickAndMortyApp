package com.example.rickandmortymodule.fragments.details

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortymodule.R
import com.example.rickandmortymodule.RickAndMortyModule
import com.example.rickandmortymodule.databinding.FragmentDetailsBinding
import com.example.rickandmortymodule.models.CharacterModel

class DetailPageFragment : Fragment() {
    private lateinit var viewModel: DetailsViewModel
    var characterId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        characterId = arguments?.getInt("id")
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        return FragmentDetailsBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = this@DetailPageFragment
            val toolbar = it.toolbar
            activity?.setActionBar(toolbar)
            activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
            activity?.actionBar?.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener {
                RickAndMortyModule.navController?.navigate(R.id.action_detailPageFragment_to_itemListFragment)
            }
            characterId?.let { id ->
                setDetails(this, id).execute()
            } ?: kotlin.run {
                activity?.onBackPressed()
            }
        }.root
    }

    class setDetails(
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
                fragment.viewModel.setView(
                    it.name,
                    it.species + " - " + it.getStatus(),
                    it.lastKnownLocation?.name,
                    it.gender,
                    it.image
                )
            }
        }
    }
}
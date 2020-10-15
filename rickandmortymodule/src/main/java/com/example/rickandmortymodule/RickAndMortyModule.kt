package com.example.rickandmortymodule

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rickandmortymodule.fragments.itemlist.ListAdapter
import com.example.rickandmortymodule.models.CharacterListModel
import com.example.rickandmortymodule.models.CharacterModel
import java.lang.Exception

object RickAndMortyModule {
    var navController: NavController? = null
    var moduleApi: ModuleApi = ModuleApi()
    var isInNavigation: Boolean = false

    fun initialize(activity: Activity, navHostLayoutId: Int) {
        navController = Navigation.findNavController(activity, navHostLayoutId)
    }

    fun getCharacters(page: Int): CharacterListModel? {
        catchAll {
            return moduleApi.getCharacters(page)
        }
        return null
    }

    fun getSelectedCharacterDetails(id: Int): CharacterModel?{
        catchAll {
            return moduleApi.getSelectedCharacterDetails(id)
        }
        return null
    }

    private inline fun catchAll(action: () -> Unit): String? {
        var message: String? = null
        try {
            action()
        } catch (e: Exception) {
            e.printStackTrace()
            message = e.message
        }
        return message
    }
}
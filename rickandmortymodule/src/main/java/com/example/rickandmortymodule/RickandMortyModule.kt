package com.example.rickandmortymodule

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rickandmortymodule.models.CharacterListModel
import java.lang.Exception

object RickandMortyModule{
    var navController: NavController? = null
    var moduleApi: ModuleApi = ModuleApi()

    fun initialize(activity: Activity, navHostLayoutId: Int) {
        navController = Navigation.findNavController(activity, navHostLayoutId)
    }
    fun getCharaters(page: Int):CharacterListModel?{
        catchAll {
            return moduleApi.getCharacters(page)
        }
        return null
    }

    inline fun catchAll(action: () -> Unit): String? {
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
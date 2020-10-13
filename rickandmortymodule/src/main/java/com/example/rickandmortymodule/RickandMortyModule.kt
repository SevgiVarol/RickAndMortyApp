package com.example.rickandmortymodule

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation

object RickandMortyModule{
    var navController: NavController? = null

    fun initialize(activity: Activity, navHostLayoutId: Int) {
        navController = Navigation.findNavController(activity, navHostLayoutId)
    }
}
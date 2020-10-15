package com.example.vlmediaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickandmortymodule.RickAndMortyModule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        RickAndMortyModule.initialize(this, R.id.custom_fragment_layout)
    }
}

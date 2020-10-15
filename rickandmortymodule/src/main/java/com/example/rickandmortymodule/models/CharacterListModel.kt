package com.example.rickandmortymodule.models

data class CharacterListModel(
    val info: InfoModel?,
    val results: ArrayList<BasicResultModel>?
)
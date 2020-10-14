package com.example.rickandmortymodule

import com.example.rickandmortymodule.models.CharacterListModel

class ModuleApi {
    private val service: WebService = WebService.getInstance()

   @Throws(Exception::class)
    fun getCharacters(page: Int?): CharacterListModel?{
        return service.getCharacters(page).execute().body()
    }
}
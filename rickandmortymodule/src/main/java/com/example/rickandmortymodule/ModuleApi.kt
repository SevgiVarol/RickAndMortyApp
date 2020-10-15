package com.example.rickandmortymodule

import com.example.rickandmortymodule.models.CharacterListModel
import com.example.rickandmortymodule.models.CharacterModel

class ModuleApi {
    private val service: WebService = WebService.getInstance()

   @Throws(Exception::class)
    fun getCharacters(page: Int?): CharacterListModel?{
        return service.getCharacters(page).execute().body()
    }

    @Throws(Exception::class)
    fun getSelectedCharacterDetails(id: Int): CharacterModel? {
        return service.getCharacterById(id).execute().body()
    }
}
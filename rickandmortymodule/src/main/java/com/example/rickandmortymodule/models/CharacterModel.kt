package com.example.rickandmortymodule.models

import com.example.rickandmortymodule.enums.GenderType
import com.example.rickandmortymodule.enums.StatusType
import com.google.gson.annotations.SerializedName

data class CharacterModel(
    val id: Int?,
    val name: String?,
    val image: String?,
    val status: String?,
    val species: String?,
    @SerializedName("location")
    val lastKnownLocation: LocationModel?,
    val gender: String?
) {
    fun getStatus(): StatusType {
        status?.let {
            return StatusType.valueOf(it)
        }
        return StatusType.unknown
    }

    fun getGender(): GenderType {
        gender?.let {
            return GenderType.valueOf(it)
        }
        return GenderType.unknown
    }
}
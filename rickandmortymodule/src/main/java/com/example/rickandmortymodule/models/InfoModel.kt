package com.example.rickandmortymodule.models

import java.net.URL

data class InfoModel(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?
) {
    fun getNextPageNum(): Int? {
        next?.let {
            var query = URL(it).query
            return query.substring(query.lastIndexOf("=") + 1).toInt()
        }
        return null
    }

    fun getPrevPageNum(): Int? {
        prev?.let {
            var query = URL(it).query
            return query.substring(query.lastIndexOf("=") + 1).toInt()
        }
        return null

    }
}
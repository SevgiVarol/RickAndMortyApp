package com.example.rickandmortymodule.fragments.itemlist

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ItemListViewModel(application: Application) : AndroidViewModel(application),
    ListAdapter.ItemClickListener {
    var itemSelected = MutableLiveData<Int>()
    override fun onItemClick(view: View, position: Int) {
        itemSelected.postValue(position)
    }

}
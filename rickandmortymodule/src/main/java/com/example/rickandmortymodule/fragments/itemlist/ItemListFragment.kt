package com.example.rickandmortymodule.fragments.itemlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortymodule.databinding.FragmentItemListBinding

class ItemListFragment : Fragment(){
    private lateinit var viewModel: ItemListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ItemListViewModel::class.java)
        return FragmentItemListBinding.inflate(inflater, container, false).also {

            
        }.root
    }

}
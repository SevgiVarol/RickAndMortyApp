package com.example.rickandmortymodule.fragments.itemlist

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortymodule.R
import com.example.rickandmortymodule.RickAndMortyModule
import com.example.rickandmortymodule.databinding.FragmentItemListBinding
import com.example.rickandmortymodule.models.BasicResultModel
import com.example.rickandmortymodule.models.CharacterListModel

class ItemListFragment : Fragment(), ListAdapter.ItemClickListener {
    private lateinit var viewModel: ItemListViewModel
    var adapter: ListAdapter = ListAdapter()
    var recyclerView: RecyclerView? = null
    var listLoading: ProgressBar? = null
    var isScrollEnded: Boolean = false
    var characterListModel: CharacterListModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ItemListViewModel::class.java)
        return FragmentItemListBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = this@ItemListFragment
            recyclerView = it.characterList
            listLoading = it.listLoading

            recyclerView?.layoutManager = CustomRecyclerviewLayoutManager(this.context)
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!isScrollEnded && listLoading?.visibility == View.GONE) {
                        var layoutManager: LinearLayoutManager =
                            recyclerView.layoutManager as LinearLayoutManager
                        var position =
                            layoutManager.findLastCompletelyVisibleItemPosition()
                        var lastIndex: Int = adapter.itemCount - 1
                        if (position == lastIndex) {
                            activity?.applicationContext?.let { it1 ->
                                loadList(this@ItemListFragment).execute()
                            }
                        }
                    }

                }
            })
            if (RickAndMortyModule.isInNavigation){
                RickAndMortyModule.isInNavigation = false
                recyclerView?.adapter = adapter
            } else{
                adapter.setClickListener(this)
                loadList(this).execute()
            }

        }.root
    }

    override fun onItemClick(view: View, position: Int) {
        var selectedCharacterId = adapter.list.get(position).id
        var position =  (recyclerView?.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        val bundle = bundleOf(
            "id" to selectedCharacterId
        )
        RickAndMortyModule.isInNavigation = true
        RickAndMortyModule.navController?.navigate(R.id.action_itemListFragment_to_detailPageFragment,bundle)
    }

    class loadList(
        itemListFragment: ItemListFragment
    ) : AsyncTask<String?, Int, Int>() {
        var itemListFragment = itemListFragment
        var pageNum: Int? = 1
        override fun onPreExecute() {
            itemListFragment.listLoading?.visibility = View.VISIBLE
            (itemListFragment.recyclerView?.layoutManager as CustomRecyclerviewLayoutManager).also {
                it.setScrollEnabled(false)
            }
        }

        override fun doInBackground(vararg p0: String?): Int {
            var oldList: ArrayList<BasicResultModel>? = ArrayList()
            var newList: ArrayList<BasicResultModel>? = null
            var currentItem: Int = 0
            itemListFragment.characterListModel?.let {
                oldList = itemListFragment.adapter.list
                currentItem = oldList?.lastIndex ?: 0
                pageNum = it.info?.getNextPageNum()
            }

            itemListFragment.characterListModel =
                pageNum?.let {
                    RickAndMortyModule.getCharacters(it)?.also {
                        it.results?.let {
                            newList = it
                        }
                    }
                }
            if (!newList.isNullOrEmpty()) {
                newList?.let { it1 ->
                    oldList?.addAll(it1)
                    oldList?.let {
                        itemListFragment.adapter.setCharacterList(it, itemListFragment.context)
                    }
                }
            } else {
                itemListFragment.isScrollEnded = true
            }


            return currentItem
        }

        override fun onPostExecute(result: Int) {
            (itemListFragment.recyclerView?.layoutManager as CustomRecyclerviewLayoutManager).also {
                it.setScrollEnabled(true)
                it.scrollToPosition(result - 1)
            }
            itemListFragment.recyclerView?.adapter = itemListFragment.adapter
            itemListFragment.listLoading?.visibility = View.GONE
        }
    }
}
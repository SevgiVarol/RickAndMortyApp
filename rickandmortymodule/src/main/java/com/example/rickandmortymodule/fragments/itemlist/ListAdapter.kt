package com.example.rickandmortymodule.fragments.itemlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortymodule.R
import com.example.rickandmortymodule.models.BasicResultModel
import com.example.rickandmortymodule.models.CharacterModel
import com.squareup.picasso.Picasso

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var listener: ItemClickListener? = null
    var list: ArrayList<BasicResultModel> = ArrayList()
    var context: Context? = null

    fun setCharacterList(characterList: ArrayList<BasicResultModel>, activity: Context?) {
        this.list = characterList
        this.context = activity
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(p0: View?) {
            listener?.onItemClick(itemView, getAdapterPosition())
        }

        var characterName: TextView
        var characterImage: ImageView

        init {
            characterName = itemView.findViewById(R.id.characterName)
            characterImage = itemView.findViewById(R.id.characterImage)
            itemView.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.listener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.custom_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = list.get(position)
        holder.characterName.setText(item.name)
        Picasso.with(context).load(item.image).into(holder.characterImage);
    }

}
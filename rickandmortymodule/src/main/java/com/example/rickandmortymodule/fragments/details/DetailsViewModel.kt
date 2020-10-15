package com.example.rickandmortymodule.fragments.details

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide


class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    var name = MutableLiveData<String?>()
    var speciesStatus = MutableLiveData<String?>()
    var location = MutableLiveData<String?>()
    var gender = MutableLiveData<String?>()
    var image = MutableLiveData<String?>()
    fun setView(
        setName: String?,
        setSpeciesStatus: String?,
        setLocation: String?,
        setGender: String?,
        setImage: String?
    ) {
        name.postValue(setName)
        speciesStatus.postValue(setSpeciesStatus)
        location.postValue(setLocation)
        gender.postValue(setGender)
        image.postValue(setImage)
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                //.apply(RequestOptions().circleCrop())
                .into(view)
        }
    }


}
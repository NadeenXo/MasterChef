package com.example.masterchef.dashboard.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.masterchef.dto.fav.FavDAO
import com.example.masterchef.dto.fav.FavouriteTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavMealsViewModel(private val dao: FavDAO) : ViewModel() {

    private val _meals: MutableLiveData<List<FavouriteTable>> = MutableLiveData()
    val meals: LiveData<List<FavouriteTable>> = _meals

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAll()
            withContext(Dispatchers.Main) {
                _meals.postValue(list)
            }
        }
    }

    fun removeFavMeal(meal: FavouriteTable) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.delete(meal)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _message.postValue("Deleted from favorites")
                } else {
                    _message.postValue("Couldn't be deleted to favorites")
                }
            }
            getData()
        }
    }
}

class FavFactory(private val dao: FavDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavMealsViewModel::class.java)) {
            return FavMealsViewModel(dao) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}

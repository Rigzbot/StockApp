package com.rishik.stockapp.viewmodels

import androidx.lifecycle.*
import com.rishik.stockapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val news = repository.getNews().asLiveData()
    val stocks = repository.getStocks().asLiveData()
    val smallListStocks = repository.getSmallStocks().asLiveData()

    //true -> searching, false -> not searching
    private val _expanded = MutableLiveData(false)
    val expanded: LiveData<Boolean>
        get() = _expanded

    fun searchingTrue() {
        _expanded.value = true
    }

    fun searchingFalse() {
        _expanded.value = false
    }
}
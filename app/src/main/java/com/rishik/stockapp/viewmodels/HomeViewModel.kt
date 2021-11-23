package com.rishik.stockapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.rishik.stockapp.database.getNewsDatabase
import com.rishik.stockapp.database.getStockDatabase
import com.rishik.stockapp.domain.Stocks
import com.rishik.stockapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getNewsDatabase(application)
    private val databaseStocks = getStockDatabase(application)
    private val repository = Repository(database, databaseStocks)

    init {
        if(repository.news.value.isNullOrEmpty()){
            viewModelScope.launch {
                repository.refreshNews()
            }
        }
        if(repository.stocks.value.isNullOrEmpty())
            viewModelScope.launch {
                repository.refreshStocks()
            }
    }

    val newsList = repository.news
    val stockList = repository.stocks

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

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}
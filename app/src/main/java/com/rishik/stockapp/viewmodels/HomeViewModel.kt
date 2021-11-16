package com.rishik.stockapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.rishik.stockapp.database.getNewsDatabase
import com.rishik.stockapp.database.getStockDatabase
import com.rishik.stockapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getNewsDatabase(application)
    private val databaseStocks = getStockDatabase(application)
    private val repository = Repository(database, databaseStocks)

    init {
        viewModelScope.launch {
            repository.refreshNews()
            repository.refreshStocks()
            _expanded.value = false
        }
    }

    val newsList = repository.news
    val stockList = repository.stocks

    //true -> searching, false -> not searching
    private val _expanded = MutableLiveData<Boolean>()
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
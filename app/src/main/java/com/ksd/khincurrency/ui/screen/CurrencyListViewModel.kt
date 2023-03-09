package com.ksd.khincurrency.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ksd.khincurrency.repository.network.Repository
import com.ksd.khincurrency.repository.network.model.CurrencyRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _currencyList = MutableLiveData<List<String>>()
    val currencyList: LiveData<List<String>> get() = _currencyList

    private var isLoadingData = false

    fun loadData() {
        if (isLoadingData) return
        isLoadingData = true
        viewModelScope.launch {
            // Load user list from data source flow. For pagination, provide the ID of the last user loaded
            val currencyListDataFlow = repository.getCurrencyList(API_APP_ID)
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    error.printStackTrace()
                    // To avoid reaching rate limits on non-auth API usage, disable loading on error by not resetting the flag
                    // isLoadingData = false
                    // Possible improvement: Error handling
                }

            currencyListDataFlow.collect { currencyInfo ->

                // val stringJSON = Gson().toJson(currencyInfo.rates).toList()  // To JSON
                // val objectList = gson.fromJson(json, Array<SomeObject>::class.java).asList()
                val gson = Gson()
                val jsonString = gson.toJson(currencyInfo.rates)
                val listType = object : TypeToken<List<String>>() { }.type
                val newList = gson.fromJson<List<String>>(jsonString, listType)
                Timber.d("String ==> ${newList.size} $newList")

                // _currencyList.value = newList
                isLoadingData = false
            }
        }
    }

    fun loadNextItemsIfNeeded(lastVisibleItem: Int, totalItemsInList: Int) {
        // Pagination: Start loading the next items onto the list when we are about to reach the end
        if (lastVisibleItem + 3 > totalItemsInList) {
            loadData()
        }
    }

    companion object {
        private const val API_APP_ID = "8fafcccc5c3e4a61b169a402551d2885"

    }

}
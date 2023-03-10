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

    private var _currencyList = MutableLiveData<List<CurrencyListItemUiState>>()
    var currencyList: MutableLiveData<List<CurrencyListItemUiState>> = _currencyList

    private var isLoadingData = false

    init {
        loadData()
    }

    private fun loadData() {
        if (isLoadingData) return
        isLoadingData = true
        viewModelScope.launch {
            // Load currency list from data source flow. For pagination, provide the ID of the last user loaded
            val currencyListDataFlow = repository.getCurrencyList(API_APP_ID)
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    error.printStackTrace()
                    // To avoid reaching rate limits on non-auth API usage, disable loading on error by not resetting the flag
                    // isLoadingData = false
                    // Possible improvement: Error handling
                }

            currencyListDataFlow.collect { currencyInfo ->

                val gson = Gson()
                val listType = object : TypeToken<Map<String,Double>>() { }.type
                val newList = gson.fromJson<Map<String,Double>>(currencyInfo.rates, listType)


                val list = newList.map {
                    CurrencyListItemUiState(baseName = it.key, rateValue = it.value)
                }
                 _currencyList.value = list
                Timber.d("New List ==========>ViewModel ${list?.size} ${list?.get(0)?.rateValue} ${list?.get(0)?.calculateAmount}")
                isLoadingData = false
            }
        }
    }

    companion object {
        private const val API_APP_ID = "8fafcccc5c3e4a61b169a402551d2885"

    }

}
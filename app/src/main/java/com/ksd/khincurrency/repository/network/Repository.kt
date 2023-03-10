package com.ksd.khincurrency.repository.network

import androidx.annotation.VisibleForTesting
import com.ksd.khincurrency.repository.network.model.CurrencyInfo
import com.ksd.khincurrency.repository.network.service.CurrencyApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class Repository @Inject constructor(
    private val currencyApiService: CurrencyApiService
) {

    companion object {
        // The cache time for the data.
        // If cache time expires or not set we retrieve from network source, else retrieve from local source
        val CURRENCY_LIST_TTL: Pair<String, Duration> = Pair("LIST", 30.minutes)
    }

    suspend fun getCurrencyList(appId: String): Flow<CurrencyInfo> = flow {
        runCatching {
            // We could expand this to cache the response based on some specific TTL for the data like this
            /*if (isCacheExpired(USER_LIST_TTL)) {
                getCurrencyListFromNetwork(since)
            } else {
                getCurrencyListFromDatabase(since)
            }*/
            // On this case for simplicity reasons we will obtain it only from network
            getCurrencyListFromNetwork(appId)
        }.onFailure { error ->
            when (error) {
                is UnknownHostException -> {
                    // No internet, we could get the data from local database if available
                    runCatching {
                        //getUserListFromDatabase()
                    }.onFailure {
                        throw it
                    }.onSuccess { _ ->
                        //emit(userList)
                    }
                }
                else -> throw error
            }
        }.onSuccess { currencyList ->
            emit(currencyList)
        }
    }

    @VisibleForTesting
    suspend fun getCurrencyListFromNetwork(appId: String): CurrencyInfo{
        // Retrieve updated data from network
        return currencyApiService.getCurrencyList(appId)
    }


}
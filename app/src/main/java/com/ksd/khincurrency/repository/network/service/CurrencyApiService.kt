package com.ksd.khincurrency.repository.network.service

import com.ksd.khincurrency.repository.network.PATH_CURRENCY
import com.ksd.khincurrency.repository.network.model.CurrencyInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET(PATH_CURRENCY)
    suspend fun getCurrencyList(@Query("app_id") appID: String): CurrencyInfo
}
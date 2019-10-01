package com.revolut.currency.networking

import com.revolut.currency.ui.rates.model.RatesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("latest?")
     fun allRates(@Query("base") lat: String): Deferred<Response<RatesResponse?>>
}
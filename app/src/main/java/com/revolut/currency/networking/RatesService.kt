package com.revolut.currency.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revolut.currency.ui.rates.model.CurrencyItem
import com.revolut.currency.ui.rates.model.RatesMappedModel
import com.revolut.currency.ui.rates.model.RatesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import java.util.Map

class RatesService(private var apiService: ApiService) : KoinComponent {


    suspend fun getAllRates() = withContext(Dispatchers.IO) {
        apiService.allRates("EUR").await()
    }

    fun convertHashmapToList(ratesResponse: RatesResponse): LiveData<RatesMappedModel> {
        val ratesModel = RatesMappedModel(ratesResponse.base, ratesResponse.date, ArrayList())
        val it = ratesResponse.rates?.ratesHashMap?.entries?.iterator()
        while (it?.hasNext()!!) {
            val pair = it.next() as Map.Entry<*, *>
            ratesModel.allCurrenciesRatesList?.add(
                CurrencyItem(
                    pair.key as String,
                    pair.value as Float
                )
            )
        }
        val mappedRates: MutableLiveData<RatesMappedModel> = MutableLiveData()
        mappedRates.value = ratesModel
        return mappedRates
    }

}
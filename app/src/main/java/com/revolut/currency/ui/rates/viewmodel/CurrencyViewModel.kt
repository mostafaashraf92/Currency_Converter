package com.revolut.currency.ui.rates.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.revolut.currency.networking.RatesService
import com.revolut.currency.ui.rates.model.RatesMappedModel
import com.revolut.currency.ui.rates.model.RatesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import java.lang.Exception


class CurrencyViewModel(
    private var ratesService: RatesService, private var coroutineScope: CoroutineScope
) : ViewModel(), KoinComponent {
    var allRatesServiceLiveData: MutableLiveData<RatesResponse> = MutableLiveData()
    var errorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var allRatesMappedLiveData: LiveData<RatesMappedModel> =
        Transformations.switchMap(allRatesServiceLiveData) {
            ratesService.convertHashmapToList(it)
        }

    init {
    }

    fun getAllRates() {

        coroutineScope.launch {
            while (true) {
                runCatching {
                    val responseRate = ratesService.getAllRates()
                    if (responseRate.isSuccessful) {
                        allRatesServiceLiveData.value = responseRate.body()
                        errorMutableLiveData.value = false

                    } else {
                        errorMutableLiveData.value = true
                    }
                }
                delay(1000)
            }
        }
    }
}
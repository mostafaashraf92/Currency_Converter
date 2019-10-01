package com.revolut.currency.ui.rates.model

data class RatesMappedModel(
    var base:String,
    var date:String,
    var allCurrenciesRatesList: ArrayList<CurrencyItem>?
)

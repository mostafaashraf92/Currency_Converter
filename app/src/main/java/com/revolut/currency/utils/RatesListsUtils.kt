package com.revolut.currency.utils

import com.revolut.currency.ui.rates.model.CurrencyItem
import com.revolut.currency.ui.rates.model.RatesMappedModel

class RatesListsUtils(var currencyUtils: CurrencyUtils) {


      fun  updateCurrentRatesFromNewRates(currentRatesList:ArrayList<CurrencyItem>,
                                          newRatesList:ArrayList<CurrencyItem>,
                                          ratesMappedModel: RatesMappedModel): ArrayList<CurrencyItem>
    {
        val baseCurrencyItem: CurrencyItem =
            currentRatesList.single { s -> s.name == ratesMappedModel.base }

        val firstItem = currentRatesList[0]

        if (!isBaseCurrency(firstItem,ratesMappedModel)) {
            val firstCurrencyNewRate =
                newRatesList.single { s -> s.name == firstItem.name }
            baseCurrencyItem.value = currencyUtils.getBaseCurrencyRateAgainstOthers(firstItem.value,
                firstCurrencyNewRate.value
            )
        }

        for (x in 1 until currentRatesList.size) {
            val currentItemFromModifiedList = currentRatesList[x]
            val itemNewRate =
                newRatesList.single { s -> s.name == currentItemFromModifiedList.name }
            currentItemFromModifiedList.value =
                currencyUtils.getCurrencyRateAgainstBase(baseCurrencyItem.value, itemNewRate.value)
        }
        return currentRatesList

    }

    private fun isBaseCurrency(currentItem: CurrencyItem, ratesMappedModel: RatesMappedModel):
            Boolean = currentItem.name ==
            ratesMappedModel.base
}
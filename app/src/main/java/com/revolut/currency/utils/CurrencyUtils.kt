package com.revolut.currency.utils

import java.text.NumberFormat
import java.util.*

class CurrencyUtils {

    fun getCurrencyRateAgainstBase(baseCurrencyValue: Float, modifiedItemValue: Float): Float {
        return baseCurrencyValue * modifiedItemValue
    }

    fun getBaseCurrencyRateAgainstOthers(currencyCurrentValue: Float, currencyRate: Float): Float {
        return currencyCurrentValue / currencyRate
    }

    fun getNumberFormatFromCurrency(currencyCode: String?, number: Float): String? {
        val format = NumberFormat.getInstance(Locale.getDefault())
        format.maximumFractionDigits = 2
        val currency = Currency.getInstance(currencyCode)
        format.currency = currency
        return format.format(number)
    }

    fun getNumberFormatFromCurrency(currencyCode: String?, number: String): Float {
        return try {
            val format = NumberFormat.getInstance(Locale.getDefault())
            format.maximumFractionDigits = 2
            val currency = Currency.getInstance(currencyCode)
            format.currency = currency
            format.parse(number).toFloat()
        } catch (exception: Exception) {
            0f
        }
    }

    fun getCountryDisplayNameFromCurrency(currencyCode: String?): String {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val currency = Currency.getInstance(currencyCode)
        return currency.displayName
    }

}
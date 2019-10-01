package com.revolut.currency.utils

import com.revolut.currency.ui.rates.model.CurrencyItem

interface EditTextListener {
    fun onEditTextFocused(currencyItem: CurrencyItem)
    fun onEditTextChanged(currencyItem: CurrencyItem, text: String)
}
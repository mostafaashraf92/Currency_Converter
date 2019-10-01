package com.revolut.currency.ui.rates.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.revolut.currency.ui.rates.model.CurrencyItem
import com.revolut.currency.utils.CurrencyUtils
import com.revolut.currency.utils.EditTextListener
import com.revolut.currency.utils.toFlagEmoji
import kotlinx.android.synthetic.main.rates_view_holder.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class ItemViewHolder constructor(
    private var view: View,
    private var editTextListener: EditTextListener
) : RecyclerView.ViewHolder(view), KoinComponent {

    private var itemPositon: Int = 0
    private val currencyUtils: CurrencyUtils by inject()
    private lateinit var currencyItem: CurrencyItem

    init {
        initEditTextListener()
    }

    private fun initEditTextListener() {
        view.currencyRateEditTxtView.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                onEditTextFocused(currencyItem, itemPositon)
            }
        }
        view.currencyRateEditTxtView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (view.currencyRateEditTxtView.hasFocus()) {
                    editTextListener.onEditTextChanged(currencyItem, s.toString())
                }
            }
        })
    }

    private fun onEditTextFocused(currencyItem: CurrencyItem, position: Int) {

            editTextListener.onEditTextFocused(currencyItem)

    }

    fun bind(currencyItem: CurrencyItem, position: Int) {
        this.currencyItem = currencyItem
        this.itemPositon = position
        view.currencyTxtView.text = currencyItem.name
        view.currenceNameTxtView.text = currencyUtils.
            getCountryDisplayNameFromCurrency(currencyItem.name)
        checkEditTextValue(currencyItem)
        view.emojiTxtView.text = currencyItem.name.toFlagEmoji()

    }

    private fun checkEditTextValue(currencyItem: CurrencyItem)
    {
        if (currencyItem.value == 0f) {
            view.currencyRateEditTxtView.text?.clear()
        } else {
            view.currencyRateEditTxtView.setText(currencyUtils.getNumberFormatFromCurrency(
                    currencyItem.name, currencyItem.value
                )
            )
        }
    }

}
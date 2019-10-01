package com.revolut.currency.ui.rates.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import com.revolut.currency.R
import com.revolut.currency.ui.rates.model.CurrencyItem
import com.revolut.currency.ui.rates.model.RatesMappedModel
import com.revolut.currency.utils.CurrencyUtils
import com.revolut.currency.utils.EditTextListener
import com.revolut.currency.utils.RatesListsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class RatesAdapter :
    RecyclerView.Adapter<ItemViewHolder>(), EditTextListener, KoinComponent {

    private val ratesListsHelper: RatesListsUtils by inject()
    private val currencyUtils: CurrencyUtils by inject()
    private lateinit var ratesMappedModel: RatesMappedModel

    override fun onEditTextFocused(currencyItem: CurrencyItem) {
        placeFocusedItemAtTop(currencyItem)
    }

    override fun onEditTextChanged(currencyItem: CurrencyItem, text: String) {
        modifyBaseCurrencyInList(currencyItem, text)
    }

    var newRatesList: ArrayList<CurrencyItem> = ArrayList()
    var currentRatesList: ArrayList<CurrencyItem> = ArrayList()

    private fun modifyBaseCurrencyInList(currencyItem: CurrencyItem, text: String) {
        currencyItem.value = currencyUtils.getNumberFormatFromCurrency(currencyItem.name, text)
        updateCurrentRatesFromNewRates()
    }


    @UiThread
    private fun updateCurrentRatesFromNewRates() {
        currentRatesList =
            ratesListsHelper.updateCurrentRatesFromNewRates(
                currentRatesList, newRatesList,
                ratesMappedModel
            )


        CoroutineScope(Dispatchers.Main).launch{
            if (currentRatesList[0].value == 0f)
                notifyDataSetChanged()
            else
                notifyItemRangeChanged(1, currentRatesList.size)
        }

    }

    fun setAdapterData(ratesMappedModel: RatesMappedModel) {
        this.ratesMappedModel = ratesMappedModel
        this.newRatesList = ratesMappedModel.allCurrenciesRatesList!!
        this.currentRatesList = newRatesList
        notifyDataSetChanged()
    }


    fun updateAdapterData(allRatesList: ArrayList<CurrencyItem>) {
        this.newRatesList = allRatesList
        updateCurrentRatesFromNewRates()
    }

    @UiThread
    private fun placeFocusedItemAtTop(currencyItem: CurrencyItem) {

        val position = currentRatesList.indexOf(currencyItem)
            if (position != 0) {
                currentRatesList.removeAt(position)
                currentRatesList.add(0, currencyItem)
                notifyItemMoved(position, 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rates_view_holder, parent,
                false
            )
            , this
        )
    }

    override fun getItemCount(): Int =
        currentRatesList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val rateItem = currentRatesList[position]
        holder.bind(rateItem, position)
    }

}
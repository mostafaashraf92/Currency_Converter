package com.revolut.currency.ui.rates.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolut.currency.ui.rates.model.CurrencyItem
import com.revolut.currency.ui.rates.model.RatesMappedModel
import com.revolut.currency.ui.rates.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currency.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import androidx.recyclerview.widget.SimpleItemAnimator
import com.revolut.currency.R


class CurrencyFragment : Fragment(), KoinComponent {

    private val mViewModel: CurrencyViewModel by viewModel()
    private val mAdapter: RatesAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            mViewModel.getAllRates()
        }
        mViewModel.allRatesMappedLiveData.observe(this, Observer<RatesMappedModel> { setData(it) })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (currencyRatesRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        currencyRatesRecyclerView
            .apply { adapter = mAdapter
            }
            .apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

            }

    }

    private fun setData(ratesMappedModel: RatesMappedModel) {
        if (mAdapter.newRatesList.isEmpty()) {
            ratesMappedModel.allCurrenciesRatesList?.let {
                it.add(0, CurrencyItem(ratesMappedModel.base, 1f))
                mAdapter.setAdapterData(ratesMappedModel)
            }
        } else {
            ratesMappedModel.allCurrenciesRatesList?.let {
                it.add(0, CurrencyItem(ratesMappedModel.base, 1f))
                mAdapter.updateAdapterData(it) }
        }
    }


}



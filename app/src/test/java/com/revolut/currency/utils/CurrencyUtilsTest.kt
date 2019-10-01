package com.revolut.currency.utils

import com.revolut.currency.di.appModule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.text.NumberFormat
import java.util.*

class CurrencyUtilsTest:KoinTest {

    private val currencyUtils:CurrencyUtils by inject()
    @Before
    fun setUp() {
        startKoin {
            modules(appModule)
        }
    }

    @Test
    fun getCurrencyRateAgainstBase() {
        val baseCurrencyValue=1f
        val currentCurrency=10f
        val actualResult=currencyUtils.getCurrencyRateAgainstBase(baseCurrencyValue,currentCurrency)
        assertEquals(actualResult,10f)
    }

    @Test
    fun getBaseCurrencyRateAgainstOthers() {
        val currentValue=10f
        val currentExhangeRate=2f
        val actualResult=currencyUtils.getBaseCurrencyRateAgainstOthers(currentValue,currentExhangeRate)
        assertEquals(actualResult,5f)
    }

    @Test
    fun getCountryDisplayNameFromCurrency_WhenCurrencyDollar_isUSD() {
        val currencyCode="USD"
        val actualName=currencyUtils.getCountryDisplayNameFromCurrency(currencyCode)
        val expectedName="US Dollar"
        assertEquals(actualName,expectedName)
    }


}
package com.revolut.currency.ui.rates.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revolut.currency.networking.ApiService
import com.revolut.currency.networking.RatesService
import com.revolut.currency.ui.rates.model.CurrencyItem
import com.revolut.currency.ui.rates.model.RatesMap
import com.revolut.currency.ui.rates.model.RatesMappedModel
import com.revolut.currency.ui.rates.model.RatesResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTest
import org.mockito.Mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class CurrencyViewModelTest : KoinTest, AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val ratesService=mockk<RatesService>()
    private lateinit var viewModel: CurrencyViewModel

    @Mock
    private lateinit var mockedDeferred: Deferred<Response<RatesResponse?>>
    @Mock
    private lateinit var mockedResult: Response<RatesResponse?>

    @Mock
    private lateinit var mockService: ApiService

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getAllRatesSuccess() {

        val mockResponse = constructActualMockedResponse()
        every { runBlocking { ratesService.getAllRates() } } returns Response.success(
                mockResponse
            )

        viewModel = CurrencyViewModel(ratesService, CoroutineScope(Dispatchers.Unconfined))
        viewModel.getAllRates()
        val response = viewModel.allRatesServiceLiveData.value
        Assert.assertEquals(response?.base, "EUR")


    }

    private fun constructActualMockedResponse(): RatesResponse {
        val map = HashMap<String, Float>()
        map["USD"] = 1.6f
        map["EUR"] = 1f
        map["INR"] = 1400f
        val ratesModel = RatesMap(map)
        return RatesResponse("EUR", "10-11-1992", ratesModel)
    }



}
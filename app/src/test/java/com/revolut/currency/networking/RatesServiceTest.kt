package com.revolut.currency.networking

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.revolut.currency.ui.rates.model.RatesMap
import com.revolut.currency.ui.rates.model.RatesResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class RatesServiceTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var ratesService: RatesService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getAllRatesSuccess() {
        runBlocking {
            val mockResponse = constructActualMockedResponse()
            var response: RatesResponse? = null
            val service = mockk<ApiService>()
            every { service.allRates(any()) } returns CompletableDeferred(
                Response.success(
                    mockResponse
                )
            )
            ratesService = RatesService(service)
            response = ratesService.getAllRates().body()
            assertEquals(response?.base, "EUR")

        }
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
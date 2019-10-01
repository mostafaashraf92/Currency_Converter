package com.revolut.currency.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.revolut.currency.ui.rates.model.RatesMap
import com.revolut.currency.ui.rates.model.RatesResponse
import java.lang.reflect.Type

class CustomDeserializer : JsonDeserializer<RatesResponse> {
    private val ratesMap_key = "rates"
    private val ratesBase_Key = "base"
    private val ratesDate_Key = "date"


    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RatesResponse {
        val response = RatesResponse("", "", null)
        val `object` = json.asJsonObject.getAsJsonObject(ratesMap_key)
        val `responseObject` = json.asJsonObject
        val ratesMap = Gson().fromJson<HashMap<String, Float>>(
            `object`,
            object : TypeToken<HashMap<String, Float>>() {

            }.type
        )
        response.base = responseObject.get(ratesBase_Key).asString
        response.date = responseObject.get(ratesDate_Key).asString
        response.rates = RatesMap(ratesMap)

        return response
    }
}
package com.plcoding.stockmarketapp.data.remote

import androidx.room.Dao
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object{
        const val API_KEY="E28PVU3HEQ7924RR"
        const val BASE_URL="https://www.alphavantage.co/"
    }
}
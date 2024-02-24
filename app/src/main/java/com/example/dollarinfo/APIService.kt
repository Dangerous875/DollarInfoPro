package com.example.dollarinfo

import com.example.dollarinfo.data.DollarResponse
import com.example.dollarinfo.data.DollarURUResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {


    @GET
    suspend fun getDollarValueListARG(@Url url: String): Response<List<DollarResponse>>

    @GET
    suspend fun getDollarValueURU(@Url url: String): Response<DollarURUResponse>

    @GET
    suspend fun getDollarValueEUR(@Url url: String): Response<DollarResponse>
}
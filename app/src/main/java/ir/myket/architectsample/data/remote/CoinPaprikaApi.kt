package ir.myket.architectsample.data.remote

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.data.remote.dto.CoinDetailDto
import ir.myket.architectsample.data.remote.dto.CoinDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDto
}
package ir.myket.architectsample.domain.repository

import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.model.CoinDetail

interface CoinRepository {

	suspend fun getCoins(): List<Coin>

	suspend fun getCoinById(coinId: String): CoinDetail
}
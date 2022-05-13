package ir.myket.architectsample.domain.repository

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

	fun getCoins(): Flow<Resource<Coin>>

	fun getCoinById(coinId: String): Flow<Resource<CoinDetail>>
}
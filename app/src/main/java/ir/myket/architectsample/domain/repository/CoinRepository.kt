package ir.myket.architectsample.domain.repository

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

	suspend fun getCoins(): Resource<Coin>

	suspend fun getCoinById(coinId: String): Resource<CoinDetail>
}
package ir.myket.architectsample.data.repository

import ir.myket.architectsample.data.remote.CoinPaprikaApi
import ir.myket.architectsample.data.remote.dto.toCoin
import ir.myket.architectsample.data.remote.dto.toCoinDetail
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.model.CoinDetail
import ir.myket.architectsample.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
	private val api: CoinPaprikaApi
) : CoinRepository {

	override suspend fun getCoins(): List<Coin> = api.getCoins().map { it.toCoin() }

	override suspend fun getCoinById(coinId: String): CoinDetail = api.getCoinById(coinId).toCoinDetail()

}
package ir.myket.architectsample.data.repository

import ir.myket.architectsample.common.Resource
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

	override suspend fun getCoins(): Resource<Coin> {
		val response = api.getCoins()
		return if (response.isSuccessful) {
			val coins = response.body()?.map { it.toCoin() }
			if (coins?.isNotEmpty() == true) {
				Resource.Success(coins[0])
			} else {
				Resource.Error("coins is null or empty")
			}
		} else {
			Resource.Error(response.errorBody().toString())
		}
	}

	override suspend fun getCoinById(coinId: String): Resource<CoinDetail> {
		val response = api.getCoinById(coinId)
		return if (response.isSuccessful) {
			val coinDetail = response.body()?.toCoinDetail()
			if (coinDetail != null) {
				Resource.Success(coinDetail)
			} else {
				Resource.Error("coinDetail is null")
			}
		} else {
			Resource.Error(response.errorBody().toString())
		}
	}
}
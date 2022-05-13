package ir.myket.architectsample.data.repository

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.data.remote.CoinPaprikaApi
import ir.myket.architectsample.data.remote.dto.toCoin
import ir.myket.architectsample.data.remote.dto.toCoinDetail
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.model.CoinDetail
import ir.myket.architectsample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
	private val api: CoinPaprikaApi
) : CoinRepository {

	override fun getCoins(): Flow<Resource<Coin>> = flow {
		emit(Resource.Loading())
		val response = api.getCoins()
		if (response.isSuccessful) {
			val coins = response.body()?.map { it.toCoin() }
			if (coins?.isNotEmpty() == true) {
				emit(Resource.Success(coins[0]))
			} else {
				emit(Resource.Error("coins is null or empty"))
			}
		} else {
			emit(Resource.Error(response.errorBody().toString()))
		}
	}

	override fun getCoinById(coinId: String): Flow<Resource<CoinDetail>> = flow {
		emit(Resource.Loading())
		val response = api.getCoinById(coinId)
		if (response.isSuccessful) {
			response.body()?.also {
				emit(Resource.Success(it.toCoinDetail()))
			} ?: emit(Resource.Error("coinDetail is null"))
		} else {
			emit(Resource.Error(response.errorBody().toString()))
		}
	}
}
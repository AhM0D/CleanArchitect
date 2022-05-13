package ir.myket.architectsample.data.repository

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.data.model.Coin
import ir.myket.architectsample.data.model.CoinDetail
import ir.myket.architectsample.data.remote.CoinPaprikaApi
import ir.myket.architectsample.data.remote.dto.toCoin
import ir.myket.architectsample.data.remote.dto.toCoinDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepository @Inject constructor(
	private val api: CoinPaprikaApi
) {

	fun getCoins(): Flow<Resource<List<Coin>>> = flow {
		try {
			emit(Resource.Loading())
			val response = api.getCoins()
			emit(Resource.Success(response.map { it.toCoin()}))
		} catch (e: HttpException) {
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
		} catch (e: IOException) {
			emit(Resource.Error("Couldn't reach server. Check your internet connection."))
		}
	}

	fun getCoinDetail(coinId: String): Flow<Resource<CoinDetail>> = flow {
		try {
			emit(Resource.Loading())
			val coin = api.getCoinById(coinId)
			emit(Resource.Success(coin.toCoinDetail()))
		} catch (e: HttpException) {
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
		} catch (e: IOException) {
			emit(Resource.Error("Couldn't reach server. Check your internet connection."))
		}
	}
}
package ir.myket.architectsample.domain.use_case.get_coins

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
	private val repository: CoinRepository
) {
	operator fun invoke(): Flow<Resource<Coin>> = flow {
		try {
			emit(Resource.Loading())
			val coinList = repository.getCoins()
			if (coinList.isEmpty()) {
				emit(Resource.Error("list is null or empty"))
			} else {
				emit(Resource.Success(coinList.random()))
			}
		} catch (e: HttpException) {
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		} catch (e: IOException) {
			emit(Resource.Error("Couldn't reach server. Check your internet connection."))
		}
	}
}
package ir.myket.architectsample.domain.use_case.get_coin_info

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.CoinInfo
import ir.myket.architectsample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomCoinInfoUseCase @Inject constructor(private val coinRepository: CoinRepository) {

	operator fun invoke(): Flow<Resource<CoinInfo>> = flow {
		emit(Resource.Loading())
		val coin = coinRepository.getCoins()
		if (coin is Resource.Success) {
			coin.data ?: emit(Resource.Error("coin is null!"))
			val coinDetail = coinRepository.getCoinById(coin.data!!.id)
			if (coinDetail is Resource.Success) {
				emit(Resource.Success(CoinInfo(coin = coin.data, coinDetail = coinDetail.data)))
			} else {
				emit(Resource.Error(coinDetail.message ?: "Unknown"))
			}
		} else {
			emit(Resource.Error(coin.message ?: "Unknown"))
		}

	}
}
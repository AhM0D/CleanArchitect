package ir.myket.architectsample.domain.use_case.get_coin_info

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.CoinInfo
import ir.myket.architectsample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomCoinInfoUseCase @Inject constructor(private val coinRepository: CoinRepository) {

	operator fun invoke(): Flow<Resource<CoinInfo>> = flow {
		emit(Resource.Loading())
		coinRepository.getCoins().firstOrNull { it is Resource.Success }?.also { it ->
			coinRepository.getCoinById(it.data?.id ?: "").firstOrNull { it is Resource.Success }
				?.also { detail ->
					emit(Resource.Success(CoinInfo(coin = it.data, coinDetail = detail.data)))
				}
		}
	}
}
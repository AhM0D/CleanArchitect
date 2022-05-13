package ir.myket.architectsample.domain.use_case.get_coin

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.CoinDetail
import ir.myket.architectsample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository) {
	operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = repository.getCoinById(coinId)
}
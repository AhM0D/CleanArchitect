package ir.myket.architectsample.domain.use_case.get_coins

import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomCoinUseCase @Inject constructor(private val repository: CoinRepository) {
	operator fun invoke(): Flow<Resource<Coin>> = repository.getCoins()
}
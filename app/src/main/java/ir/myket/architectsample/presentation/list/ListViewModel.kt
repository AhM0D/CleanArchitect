package ir.myket.architectsample.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.model.CoinInfo
import ir.myket.architectsample.domain.use_case.get_coin_info.GetRandomCoinInfoUseCase
import ir.myket.architectsample.domain.use_case.get_coins.GetRandomCoinUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
	private val getRandomCoinUseCase: GetRandomCoinUseCase,
	private val getRandomCoinInfoUseCase: GetRandomCoinInfoUseCase
) : ViewModel() {

	private val _coinListState: MutableLiveData<Resource<Coin>> = MutableLiveData(null)
	val coinsListState: LiveData<Resource<Coin>> = _coinListState

	private val _randomCoinInfo: MutableLiveData<Resource<CoinInfo>> = MutableLiveData(null)
	val randomCoinInfo: LiveData<Resource<CoinInfo>> = _randomCoinInfo

	fun getCoins() {
		if (coinsListState.value == null) {
			getRandomCoinUseCase().onEach {
				_coinListState.value = it
			}.launchIn(viewModelScope)
		}
		if (randomCoinInfo.value == null) {
			getRandomCoinInfoUseCase().onEach {
				_randomCoinInfo.value = it
			}.launchIn(viewModelScope)
		}
	}
}
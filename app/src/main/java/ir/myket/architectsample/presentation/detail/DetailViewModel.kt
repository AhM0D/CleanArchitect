package ir.myket.architectsample.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.CoinDetail
import ir.myket.architectsample.domain.use_case.get_coin.GetCoinUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getCoinUseCase: GetCoinUseCase) : ViewModel() {

	private val _coinDetail: MutableLiveData<Resource<CoinDetail>> = MutableLiveData(null)
	val coinDetail: LiveData<Resource<CoinDetail>> = _coinDetail

	fun getCoinDetail(id: String) {
		if (coinDetail.value == null) {
			getCoinDetailFromServer(id)
		}
	}

	private fun getCoinDetailFromServer(id: String) {
		getCoinUseCase(id).onEach {
			_coinDetail.value = it
		}.launchIn(viewModelScope)
	}
}
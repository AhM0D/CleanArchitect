package ir.myket.architectsample.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.data.model.CoinDetail
import ir.myket.architectsample.data.repository.CoinRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val coinRepository: CoinRepository) : ViewModel() {

	private val _coinDetail: MutableLiveData<CoinDetailState> = MutableLiveData(null)
	val coinDetail: LiveData<CoinDetailState> = _coinDetail

	fun getCoinDetail(id: String) {
		if (coinDetail.value == null) {
			getCoinDetailFromServer(id)
		}
	}

	private fun getCoinDetailFromServer(id: String) {
		coinRepository.getCoinDetail(id).onEach { result ->
			when (result) {
				is Resource.Success -> {
					result.data?.also {
						_coinDetail.value = CoinDetailState(it, null, false)
					} ?: run {
						_coinDetail.value = CoinDetailState(null, Error("Coin detail is null!"), false)
					}
				}
				is Resource.Error -> {
					_coinDetail.value = CoinDetailState(null, Error(result.message), false)
				}
				is Resource.Loading -> {
					_coinDetail.value = CoinDetailState(null, null, true)
				}
			}
		}.launchIn(viewModelScope)
	}
}
package ir.myket.architectsample.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.data.repository.CoinRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val coinRepository: CoinRepository) : ViewModel() {

	private val _coinListState: MutableLiveData<ListViewState> = MutableLiveData(null)
	val coinsListState: LiveData<ListViewState> = _coinListState

	fun getCoins() {
		if (coinsListState.value == null) {
			getCoinsFromServer()
		}
	}

	private fun getCoinsFromServer() {
		coinRepository.getCoins().onEach { result ->
			when (result) {
				is Resource.Success -> {
					result.data?.also {
						_coinListState.value = ListViewState(it.random(), null, false)
					} ?: run {
						_coinListState.value = ListViewState(null, Error("detail is null!"), false)
					}
				}
				is Resource.Error -> {
					_coinListState.value = ListViewState(null, Error(result.message), false)
				}
				is Resource.Loading -> {
					_coinListState.value = ListViewState(null, null, true)
				}
			}
		}.launchIn(viewModelScope)
	}
}
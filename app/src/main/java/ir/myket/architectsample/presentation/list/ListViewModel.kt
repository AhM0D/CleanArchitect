package ir.myket.architectsample.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.domain.model.Coin
import ir.myket.architectsample.domain.use_case.get_coins.GetCoinsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getCoinsUseCase: GetCoinsUseCase) : ViewModel() {

	private val _coinListState: MutableLiveData<Resource<Coin>> = MutableLiveData(null)
	val coinsListState: LiveData<Resource<Coin>> = _coinListState

	fun getCoins() {
		if (coinsListState.value == null) {
			getCoinsUseCase().onEach {
				_coinListState.value = it
			}.launchIn(viewModelScope)
		}
	}
}
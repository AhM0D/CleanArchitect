package ir.myket.architectsample.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.myket.architectsample.R
import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.databinding.FragmentCoinListBinding
import ir.myket.architectsample.domain.model.Coin

@AndroidEntryPoint
class ListContentFragment : Fragment(R.layout.fragment_coin_list) {

	private val binding: FragmentCoinListBinding by lazy(LazyThreadSafetyMode.NONE) {
		FragmentCoinListBinding.inflate(layoutInflater)
	}

	val viewModel: ListViewModel by viewModels(ownerProducer = { this })

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.getCoins()
		viewModel.coinsListState.observe(viewLifecycleOwner) {
			binding.progressBar.isVisible = it is Resource.Loading
			when (it) {
				is Resource.Success -> handleCoinInfo(it.data)
				is Resource.Error -> handleErrorMessage(it.message ?: "")
				else -> {}
			}
		}

		binding.btn.setOnClickListener {
			findNavController().navigate(
				R.id.action_list_to_detail,
				bundleOf("id" to viewModel.coinsListState.value?.data?.id)
			)
		}
	}

	private fun handleCoinInfo(coin: Coin?) {
		binding.content.text = coin.toString()
		binding.content.isVisible = true
	}

	private fun handleErrorMessage(message: String) {
		binding.errorMessage.text = message
		binding.errorMessage.isVisible = true
	}
}
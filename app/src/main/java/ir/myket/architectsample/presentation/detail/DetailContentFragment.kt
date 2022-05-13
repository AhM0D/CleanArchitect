package ir.myket.architectsample.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.myket.architectsample.R
import ir.myket.architectsample.common.Resource
import ir.myket.architectsample.databinding.FragmentCoinDetailBinding
import ir.myket.architectsample.domain.model.CoinDetail

@AndroidEntryPoint
class DetailContentFragment : Fragment(R.layout.fragment_coin_detail) {

	private val binding: FragmentCoinDetailBinding by lazy(LazyThreadSafetyMode.NONE) {
		FragmentCoinDetailBinding.inflate(layoutInflater)
	}

	val viewModel: DetailViewModel by viewModels(ownerProducer = { this })

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.getCoinDetail(requireArguments().getString("id") ?: "0")
		viewModel.coinDetail.observe(viewLifecycleOwner) {
			binding.progressBar.isVisible = it is Resource.Loading
			when (it) {
				is Resource.Success -> handleCoinDetail(it.data)
				is Resource.Error -> handleErrorMessage(it.message ?: "")
				else -> {}
			}
		}
	}

	private fun handleCoinDetail(coinDetail: CoinDetail?) {
		binding.content.text = coinDetail.toString()
		binding.content.isVisible = true
	}

	private fun handleErrorMessage(message: String) {
		binding.errorMessage.text = message
		binding.errorMessage.isVisible = true
	}
}
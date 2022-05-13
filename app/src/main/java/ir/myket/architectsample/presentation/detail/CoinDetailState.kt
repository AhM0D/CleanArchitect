package ir.myket.architectsample.presentation.detail

import ir.myket.architectsample.data.model.CoinDetail

data class CoinDetailState(
	val coin: CoinDetail ?= null,
	val error: Error?,
	var isLoading: Boolean = true
)
package ir.myket.architectsample.presentation.list

import ir.myket.architectsample.data.model.Coin

data class ListViewState(
	val coin: Coin ?= null,
	val error: Error?,
	var isLoading: Boolean = true
)
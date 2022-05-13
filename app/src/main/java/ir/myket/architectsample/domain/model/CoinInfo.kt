package ir.myket.architectsample.domain.model

data class CoinInfo(
	val coin: Coin?,
	val coinDetail: CoinDetail?
) {
	override fun toString(): String {
		return "coin= $coin \n\n coinDetail= $coinDetail"
	}
}
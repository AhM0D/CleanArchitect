package ir.myket.architectsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.myket.architectsample.common.Constants
import ir.myket.architectsample.data.remote.CoinPaprikaApi
import ir.myket.architectsample.data.repository.CoinRepositoryImpl
import ir.myket.architectsample.domain.repository.CoinRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

	@Provides
	@Singleton
	fun providePaprikaApi(): CoinPaprikaApi {
		return Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(CoinPaprikaApi::class.java)
	}

	@Provides
	@Singleton
	fun provideCoinRepository(api: CoinPaprikaApi): CoinRepository {
		return CoinRepositoryImpl(api)
	}

}
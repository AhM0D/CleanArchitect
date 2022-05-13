package ir.myket.architectsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.myket.architectsample.common.Constants
import ir.myket.architectsample.data.remote.CoinPaprikaApi
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

}
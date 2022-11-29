package ru.vk.homework2

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class DataResult(
	@SerializedName("data") val res: List<DataObject>
)

data class DataObject(
	@SerializedName("id") val id: String,
	@SerializedName("images") val images: DataImage
)

data class DataImage(
	@SerializedName("original") val ogImage: ogImage
)

data class ogImage(
	val url: String
)

interface CatsAccessor {
	@GET("gifs/trending")
	suspend fun getCats(@Query("q") query: String, @Query("limit") limit: Int, @Query("api_key") api_key: String, @Query("offset") offset: Int) : DataResult

	companion object {
		const val baseUrl = "https://api.giphy.com/v1/"

		fun create(): CatsAccessor {
			val loggingInterceptor = HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			}

			val client = OkHttpClient.Builder().apply {
				addNetworkInterceptor(loggingInterceptor)
			}.build()

			val retrofit = Retrofit.Builder().apply {
				client(client)
				addConverterFactory(GsonConverterFactory.create())
				baseUrl(baseUrl)
			}.build()

			return retrofit.create(CatsAccessor::class.java)
		}
	}
}
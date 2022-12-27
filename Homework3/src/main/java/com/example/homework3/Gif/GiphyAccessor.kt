package com.example.homework3.Gif

import com.google.gson.annotations.SerializedName
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

interface GiphyAccessor {
	@GET("gifs/search")
	suspend fun getGif(@Query("q") query: String, @Query("limit") limit: Int, @Query("api_key") api_key: String, @Query("offset") offset: Int) : DataResult

	companion object {
		private const val baseUrl = "https://api.giphy.com/v1/"

		fun create(): GiphyAccessor {
			val retrofit = Retrofit.Builder().apply {
				addConverterFactory(GsonConverterFactory.create())
				baseUrl(baseUrl)
			}.build()

			return retrofit.create(GiphyAccessor::class.java)
		}
	}
}
package com.example.homework3.Gif

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsAccessor {
	@GET("gifs/search")
	suspend fun getGifs(@Query("q") query: String, @Query("limit") limit: Int, @Query("api_key") api_key: String, @Query("offset") offset: Int) : DataResult

	companion object {
		private const val baseUrl = "https://api.giphy.com/v1/"

		fun create(): GifsAccessor {
			val retrofit = Retrofit.Builder().apply {
				addConverterFactory(GsonConverterFactory.create())
				baseUrl(baseUrl)
			}.build()

			return retrofit.create(GifsAccessor::class.java)
		}
	}
}
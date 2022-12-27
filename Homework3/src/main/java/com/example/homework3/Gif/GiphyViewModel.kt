package com.example.homework3.Gif

import androidx.lifecycle.ViewModel

class GiphyViewModel : ViewModel() {
	private var offset: Int = 0
	private val accessor = GiphyAccessor.create()

	suspend fun getGif(): DataObject? {
		val gif: DataResult?
		try {
			gif = accessor.getGif(
				searchWord , limit, apiKey, offset++
			)
		} catch (e: Exception) {
			return null
 		}
		return gif.res[0]
	}

	companion object {
		private const val searchWord = "night street"
		private const val limit = 1
		private const val apiKey = "iKDUmbu82TkpMSPJ09O4FX3xvoSIfM3b"
	}
}
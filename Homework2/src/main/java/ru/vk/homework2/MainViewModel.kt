package ru.vk.homework2

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
	private var offset: Int = 16
	private val accessor = GiphyAccessor.create()
	val gifAdapter = GifAdapter()

	suspend fun getGifs(limit: Int): List<DataObject> {
		if (limit == 20) {
			return accessor.getGifs(
				"cat" , limit, "7lJF1UHRRVsX9F5Q8BLc5llXuCe92Nzt", 0
			).res
		}
		else {
			offset += 4
			return accessor.getGifs(
				"cat" , limit, "7lJF1UHRRVsX9F5Q8BLc5llXuCe92Nzt", offset
			).res
		}
	}
}
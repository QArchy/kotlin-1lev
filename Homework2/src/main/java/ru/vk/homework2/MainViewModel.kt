package ru.vk.homework2

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
	private var offset: Int = 46
	private val accessor by lazy { CatsAccessor.create() }

	suspend fun getCats(limit: Int): List<DataObject> {
		if (limit == 50) {
			return accessor.getCats(
				limit, "7lJF1UHRRVsX9F5Q8BLc5llXuCe92Nzt", 0
			).res
		}
		else {
			offset += 4
			return accessor.getCats(
				limit, "7lJF1UHRRVsX9F5Q8BLc5llXuCe92Nzt", offset
			).res
		}
	}
}
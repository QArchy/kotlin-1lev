package com.example.homework3.Gif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework3.Database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GifsViewModel : ViewModel() {

	private val gifsRepository: GifsRepository = GifsRepository(GifsAccessor.create())

	suspend fun getGif(): DataObject? {
		return gifsRepository.getGifs()
	}
}
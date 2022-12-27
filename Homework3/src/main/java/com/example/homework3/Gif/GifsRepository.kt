package com.example.homework3.Gif

class GifsRepository(private val giphsAccessor: GifsAccessor) {

    private var offset = 0

    suspend fun getGifs(): DataObject? {
        val gif: DataResult?

        try {
            gif = giphsAccessor.getGifs(
                searchWord, limit, apiKey, offset++
            )
        } catch (e: Exception) {
            return null
        }

        return gif.res[0]
    }

    companion object {
        private const val searchWord = "cyberpunk 2077 wallpaper"
        private const val limit = 1
        private const val apiKey = "iKDUmbu82TkpMSPJ09O4FX3xvoSIfM3b"
    }
}
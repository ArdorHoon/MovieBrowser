package com.ardor.data.remote.model

data class SearchResult(
    val Response: String? = "",
    val Search: List<Search>? = listOf(),
    val totalResults: String? = ""
)

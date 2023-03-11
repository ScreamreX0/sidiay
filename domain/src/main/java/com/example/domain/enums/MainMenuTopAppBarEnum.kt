package com.example.domain.enums

enum class MainMenuTopAppBarEnum(
    val id: Int,
    val icon: Int,
) {
    SEARCH(0, com.example.core.R.drawable.ic_baseline_search_24_white),
    FILTER(1, com.example.core.R.drawable.ic_baseline_filter_list_24_white),
    CREATE(2, com.example.core.R.drawable.ic_baseline_add_24_white),
    REFRESH(3, com.example.core.R.drawable.baseline_refresh_24),
}
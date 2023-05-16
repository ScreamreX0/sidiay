package com.example.home.ui.tickets_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun <T> CustomScrollableTabRow(
    pagerState: PagerState,
    values: Array<T>,
    valueTitle: (T) -> String
) {
    val scrollCoroutineScope = rememberCoroutineScope()
    ScrollableTabRow(
        modifier = Modifier.height(40.dp),
        selectedTabIndex = pagerState.currentPage,
        contentColor = MaterialTheme.colors.onBackground,
        edgePadding = 0.dp,
        tabs = {
            values.forEachIndexed { index, it ->
                Tab(
                    modifier = Modifier.background(MaterialTheme.colors.background),
                    selected = pagerState.currentPage == index,
                    onClick = { scrollCoroutineScope.launch { pagerState.scrollToPage(index) } },
                    text = {
                        Text(
                            text = valueTitle(it),
                            color = MaterialTheme.colors.onBackground,
                            fontSize = MaterialTheme.typography.h3.fontSize
                        )
                    }
                )
            }
        }
    )
}
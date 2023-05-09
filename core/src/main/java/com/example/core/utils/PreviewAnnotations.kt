package com.example.core.utils

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


const val locale = "ru"
const val device = Devices.PIXEL_2

@Preview(
    device = device,
    uiMode = UI_MODE_NIGHT_NO,
    locale = locale,
    group = "screens",
    backgroundColor = 0
)
@Preview(
    device = device,
    uiMode = UI_MODE_NIGHT_YES,
    locale = locale,
    group = "screens"
)
annotation class ScreenPreview


@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    locale = locale,
    group = "components"
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    locale = locale,
    group = "components"
)
annotation class ComponentPreview
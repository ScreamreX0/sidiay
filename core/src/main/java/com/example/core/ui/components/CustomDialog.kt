package com.example.core.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme

@Composable
fun CustomAlertDialog(
    title: String,
    isDialogOpened: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = { isDialogOpened.value = false }
) {
    AppTheme(isSystemInDarkTheme()) {
        AlertDialog(
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            backgroundColor = MaterialTheme.colors.background,
            title = {
                Text(
                    color = MaterialTheme.colors.onBackground,
                    text = title
                )
            },
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Text(
                    modifier = Modifier
                        .clickable { onConfirm() }
                        .padding(end = 10.dp, bottom = 5.dp),
                    color = MaterialTheme.colors.onBackground,
                    text = AnnotatedString("Да"),
                )
            },
            dismissButton = {
                Text(
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(end = 10.dp, bottom = 5.dp),
                    color = MaterialTheme.colors.onBackground,
                    text = AnnotatedString("Отмена"),
                )
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CustomAlertDialog(
        title = "Hello world",
        isDialogOpened = remember { mutableStateOf(false) },
        {},
        {}
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewNightMode() {
    CustomAlertDialog(
        title = "Hello world",
        isDialogOpened = remember { mutableStateOf(false) },
        {},
        {}
    )
}
package com.example.signin.ui.signin.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.core.R
import com.example.core.ui.theme.DefaultTextStyle
import com.example.domain.models.params.ConnectionParams
import com.example.domain.enums.states.ConnectionState
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty0

internal class CheckConnectionComponent {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier,
            selectedConnection: MutableState<ConnectionParams>,
            checkConnection: suspend (String) -> Unit,
            checkConnectionResult: KProperty0<MutableState<ConnectionState>>
        ) {
            val scope = rememberCoroutineScope()

            val context = LocalContext.current
            val checkResult = remember { checkConnectionResult.get() }
            SnackBar(
                checkResult = checkResult,
                context = context,
            )

            DefaultTextStyle {
                Text(
                    modifier = modifier
                        .clickable {
                            scope.launch {
                                checkConnection.invoke(
                                    selectedConnection.value.url,
                                )
                            }
                        },
                    text = stringResource(id = R.string.check_connection),
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }

        @Composable
        private fun SnackBar(
            checkResult: MutableState<ConnectionState>,
            context: Context,
        ) {
            if (checkResult.value == ConnectionState.WAITING) {
                return
            }

            Toast.makeText(
                context,
                stringResource(checkResult.value.nameId!!),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
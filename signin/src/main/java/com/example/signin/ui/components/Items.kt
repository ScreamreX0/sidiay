package com.example.signin.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.utils.Helper
import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.enums.states.ConnectionState
import kotlinx.coroutines.launch

@Composable
internal fun AutoAuthComponent(
    modifier: Modifier = Modifier,
    autoAuth: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = autoAuth.value,
            onCheckedChange = { autoAuth.value = it },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.onBackground,
                uncheckedColor = MaterialTheme.colors.onBackground,
                checkmarkColor = MaterialTheme.colors.background,
            )
        )
        Text(
            text = "Входить автоматически",
            fontSize = MaterialTheme.typography.h2.fontSize,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
internal fun CheckConnectionComponent(
    modifier: Modifier,
    selectedConnection: MutableState<ConnectionParams>,
    checkConnection: suspend (String) -> Unit,
    checkConnectionResult: MutableState<ConnectionState>
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    checkConnectionResult.value.title?.let { Helper.showShortToast(context, it) }

    DefaultTextStyle {
        Text(
            modifier = modifier.clickable {
                scope.launch { checkConnection.invoke(selectedConnection.value.url) }
            },
            text = "Проверить соединение",
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
internal fun ConnectionComponent(
    modifier: Modifier = Modifier,
    isConnectionDialogOpened: MutableState<Boolean>,
    selectedConnection: MutableState<ConnectionParams>,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { isConnectionDialogOpened.value = true },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
        shape = RoundedCornerShape(25),
    ) {
        Text(
            text = selectedConnection.value.name,
            fontSize = MaterialTheme.typography.h3.fontSize,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
internal fun EmailComponent(
    modifier: Modifier = Modifier,
    email: MutableState<String> = remember { mutableStateOf("") }
) {
    OutlinedTextField(
        value = email.value,
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = MaterialTheme.colors.onBackground,
            textColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.onBackground,
            unfocusedLabelColor = MaterialTheme.colors.onBackground,
            cursorColor = MaterialTheme.colors.onBackground,
        ),
        label = {
            Text(
                text = "Email",
                fontSize = MaterialTheme.typography.h3.fontSize
            )
        },
        onValueChange = { email.value = it }
    )
}

@Composable
internal fun EnterComponent(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    password: MutableState<String>,
    rememberMe: MutableState<Boolean> = remember { mutableStateOf(false) },
    selectedConnection: MutableState<ConnectionParams>,
    signInFunction: (String, String, String) -> Unit
) {
    DefaultButtonStyle {
        Button(
            modifier = modifier,
            onClick = {
                signInFunction(
                    selectedConnection.value.url,
                    email.value,
                    password.value,
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onBackground,
            )
        ) {
            Text(
                text = "Войти",
                fontSize = MaterialTheme.typography.h3.fontSize,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
internal fun OfflineModeComponent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.popBackStack()
                navController.navigate(Graphs.MAIN_MENU)
            },
        text = "Автономный режим",
        color = MaterialTheme.colors.onBackground,
        fontWeight = FontWeight.Bold
    )
}

@Composable
internal fun PasswordComponent(
    modifier: Modifier = Modifier,
    password: MutableState<String> = mutableStateOf(""),
    passwordVisible: MutableState<Boolean> = remember { mutableStateOf(false) },
) {
    OutlinedTextField(
        value = password.value,
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        label = {
            Text(
                text = "Пароль",
                fontSize = MaterialTheme.typography.h3.fontSize,
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = MaterialTheme.colors.onBackground,
            textColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.onBackground,
            unfocusedLabelColor = MaterialTheme.colors.onBackground,
            cursorColor = MaterialTheme.colors.onBackground,
        ),
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        onValueChange = { password.value = it },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image: Int = if (passwordVisible.value) {
                R.drawable.baseline_visibility_24
            } else {
                R.drawable.baseline_visibility_off_24
            }

            Icon(
                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value },
                painter = painterResource(id = image),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        }
    )
}

@Composable
internal fun TitleComponent(
    modifier: Modifier = Modifier,
    changeUIMode: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .size(
                width = 250.dp,
                height = 65.dp
            ),
    ) {
        Text(
            modifier = Modifier.padding(end = 5.dp),
            letterSpacing = 2.sp,
            color = MaterialTheme.colors.secondary,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.auth_title_1),
        )

        Text(
            modifier = Modifier.padding(end = 5.dp),
            fontSize = 40.sp,
            letterSpacing = 2.sp,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.auth_title_2),
        )

        val interactionSource = remember { MutableInteractionSource() }
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { changeUIMode() },
            contentScale = ContentScale.Inside,
            painter = painterResource(id = R.drawable.ic_logo),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
            contentDescription = "App logo"
        )
    }
}
package com.example.signin.ui.signin

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.core.R
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultButtonStyle


@Composable
internal fun TitleComponent(modifier: Modifier, isDarkTheme: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .size(
                width = 250.dp,
                height = 65.dp
            ),
    ) {
        Text(
            modifier = Modifier
                .padding(end = 5.dp),
            letterSpacing = 2.sp,
            color = MaterialTheme.colors.secondary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.auth_title_1),
        )

        Text(
            modifier = Modifier
                .padding(end = 5.dp),
            fontSize = 32.sp,
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
                ) {
                    isDarkTheme.value = !isDarkTheme.value
                },
            contentScale = ContentScale.Inside,
            painter = painterResource(id = R.drawable.ic_logo),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
            contentDescription = ""
        )
    }
}

@Composable
internal fun EmailTextFieldComponent(modifier: Modifier, email: MutableState<String>) {
    OutlinedTextField(
        value = email.value,
        modifier = modifier
            .padding(start = 55.dp, end = 55.dp)
            .fillMaxWidth(),
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
                text = stringResource(R.string.email),
                fontSize = MaterialTheme.typography.h3.fontSize
            )
        },
        onValueChange = { email.value = it }
    )
}

@Composable
internal fun PasswordTextFieldComponent(modifier: Modifier, password: MutableState<String>) {
    val passwordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password.value,
        modifier = modifier
            .padding(start = 55.dp, end = 55.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        label = {
            Text(
                text = stringResource(R.string.password),
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

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    )
}

@Composable
internal fun EnterButtonComponent(
    modifier: Modifier,
    navController: NavHostController,
    email: MutableState<String>,
    password: MutableState<String>,
    rememberMe: MutableState<Boolean>
) {
    DefaultButtonStyle {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 55.dp, end = 55.dp),
            onClick = {
                navController.popBackStack()
                navController.navigate(Graphs.MAIN_MENU)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onBackground,
            )
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                fontSize = MaterialTheme.typography.h3.fontSize,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
internal fun RememberComponent(
    modifier: Modifier,
    rememberMe: MutableState<Boolean>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = rememberMe.value,
            onCheckedChange = {
                rememberMe.value = it
            },

            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.onBackground,
                uncheckedColor = MaterialTheme.colors.onBackground,
                checkmarkColor = MaterialTheme.colors.background,
            )
        )
        Text(
            text = stringResource(id = R.string.remember_me),
            fontSize = MaterialTheme.typography.h2.fontSize,
            color = MaterialTheme.colors.onBackground
        )
    }

}

@Composable
internal fun ForgotPasswordComponent(
    navController: NavHostController,
    modifier: Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {

            },
        text = stringResource(id = R.string.forgot_password),
        color = MaterialTheme.colors.onBackground,
        fontWeight = FontWeight.Bold
    )
}

@Composable
internal fun OfflineModeComponent(modifier: Modifier, navController: NavHostController) {
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
        text = stringResource(id = R.string.offline_mode),
        color = MaterialTheme.colors.onBackground,
        fontWeight = FontWeight.Bold
    )
}

//
// PREVIEWS
//
@Preview(locale = "ru", group = "enterButton")
@Composable
private fun Preview() {
    AppTheme {
        //PasswordTextField(modifier = Modifier, password = remember { mutableStateOf("") })
        RememberComponent(modifier = Modifier, rememberMe = remember { mutableStateOf(true) })
        //EnterButtonComponent(modifier = Modifier, navController = rememberNavController(),email = remember { mutableStateOf("") }, password = remember { mutableStateOf("") }, rememberMe = remember { mutableStateOf(false) })
        //ForgotPasswordComponent(navController = rememberNavController(), modifier = Modifier)
        //OfflineModeComponent(modifier = Modifier, navController = rememberNavController())
        //TitleComponent(modifier = Modifier)
    }
}

@Preview(locale = "ru", uiMode = UI_MODE_NIGHT_YES, showBackground = true, group = "enterButton")
@Composable
private fun PreviewNight() {
    AppTheme {
        //PasswordTextField(modifier = Modifier, password = remember { mutableStateOf("") })
        RememberComponent(modifier = Modifier, rememberMe = remember { mutableStateOf(true) })
        //EnterButtonComponent(modifier = Modifier, navController = rememberNavController(), email = remember { mutableStateOf("") }, password = remember { mutableStateOf("") }, rememberMe = remember { mutableStateOf(false) })
        //ForgotPasswordComponent(navController = rememberNavController(), modifier = Modifier)
        //OfflineModeComponent(modifier = Modifier, navController = rememberNavController())
        //TitleComponent(modifier = Modifier)
    }
}
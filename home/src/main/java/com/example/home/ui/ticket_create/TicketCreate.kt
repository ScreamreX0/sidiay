package com.example.home.ui.ticket_create

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.BottomBarNav
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.Debugger
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.core.R


class TicketCreate {
    @Composable
    fun TicketCreateScreen(
        navController: NavHostController,
        authParams: AuthParams?,
        ticketCreateViewModel: TicketCreateViewModel = hiltViewModel(),
        draft: MutableState<DraftEntity> = remember { mutableStateOf(DraftEntity()) },
    ) {
        Content(
            navController = navController,
            authParams = authParams,
            draft = draft,
        )
    }

    @Composable
    private fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: AuthParams? = AuthParams(),
        draft: MutableState<DraftEntity> = remember { mutableStateOf(DraftEntity()) }
    ) {
        ConstraintLayout(
            constraintSet = getConstraints(),
            modifier = Modifier.fillMaxSize(),
        ) {
            /** Top app bar */
            Row(
                modifier = Modifier
                    .layoutId("topAppBarRef")
                    .height(60.dp)
                    .background(MaterialTheme.colors.primary),
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            Debugger.printInfo("Clicked")
                            navController.popBackStack(
                                route = BottomBarNav.Home.route,
                                inclusive = false
                            )
                        },
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.onPrimary,
                )

                Icon(
                    modifier = Modifier
                        .fillMaxHeight(),
                    painter = painterResource(id = R.drawable.baseline_save_as_24),
                    contentDescription = "Save draft",
                    tint = MaterialTheme.colors.onPrimary,
                )

                Icon(
                    modifier = Modifier
                        .fillMaxHeight(),
                    painter = painterResource(id = R.drawable.baseline_format_clear_24),
                    contentDescription = "Clear all fields",
                    tint = MaterialTheme.colors.onPrimary,
                )
            }


            /** Middle section */
            val scrollableState = rememberScrollState()
            Column(
                modifier = Modifier
                    .layoutId("ticketCreateRef")
                    .background(MaterialTheme.colors.background)
                .verticalScroll(scrollableState),
            ) {
                Title(
                    modifier = Modifier
                        .layoutId("requiredFieldsRef"),
                    text = "Обязательные поля",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                Text(
                    modifier = Modifier
                        .layoutId("objectsRef"),
                    text = "Объекты",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                Text(
                    modifier = Modifier
                        .layoutId("serviceRef"),
                    text = "Сервисы",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                TextField(
                    modifier = Modifier
                        .layoutId("nameRef"),
                    value = draft.value.name ?: "",
                    label = { Text("[Название заявки]") },
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onPrimary,
                        disabledTextColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.6F),
                        backgroundColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colors.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colors.onPrimary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        disabledIndicatorColor = MaterialTheme.colors.onPrimary
                    )
                )
            }

            /** Bottom app bar */
            Row(
                modifier = Modifier
                    .layoutId("bottomAppBarRef")
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5F)
                        .background(MaterialTheme.colors.primary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Сохранить",
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                // Divider
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(MaterialTheme.colors.background)
                ) {}

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5F)
                        .background(MaterialTheme.colors.primary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Отмена",
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }

    @Composable
    private fun Title(
        modifier: Modifier,
        text: String,
        fontSize: TextUnit
    ) {
        Text(
            modifier = modifier,
            text = text,
            fontSize = fontSize,
            overflow = TextOverflow.Visible,
            fontWeight = FontWeight.Bold,
        )
    }

    private fun getConstraints() = ConstraintSet {
        val topAppBarRef = createRefFor("topAppBarRef")
        val ticketCreateRef = createRefFor("ticketCreateRef")
        val bottomAppBarRef = createRefFor("bottomAppBarRef")

        constrain(topAppBarRef) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
        constrain(bottomAppBarRef) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
        constrain(ticketCreateRef) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(topAppBarRef.bottom)
            bottom.linkTo(bottomAppBarRef.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

//        // Required fields
//        val requiredFieldsRef = createRefFor("requiredFieldsRef")
//        val objectsRef = createRefFor("objectsRef")
//        val serviceRef = createRefFor("serviceRef")
//        val kindRef = createRefFor("kindRef")
//        val priorityRef = createRefFor("priorityRef")
//
//        // Optional fields
//        val optionalFieldsRef = createRefFor("optionalFieldsRef")
//        val nameRef = createRefFor("nameRef")
//        val completedWorkRef = createRefFor("completedWorkRef")
//        val executorRef = createRefFor("executorRef")
//        val brigadeRef = createRefFor("brigadeRef")
//        val planeDateRef = createRefFor("planeDateRef")
//        val expirationDateRef = createRefFor("expirationDateRef")
//        val descriptionRef = createRefFor("descriptionRef")
//
//        // Automatic fields
//        val automaticFieldsRef = createRefFor("automaticFieldsRef")
//        val statusRef = createRefFor("statusRef")
//        val authorRef = createRefFor("authorRef")
//        val creationDateRef = createRefFor("creationDateRef")
//
//        constrain(requiredFieldsRef) {
//            start.linkTo(parent.start, margin = 10.dp)
//            end.linkTo(parent.end, margin = 10.dp)
//            top.linkTo(parent.top, margin = 5.dp)
//            width = Dimension.fillToConstraints
//        }
//
//        constrain(objectsRef) {
//            start.linkTo(parent.start, margin = 10.dp)
//            end.linkTo(parent.end, margin = 10.dp)
//            top.linkTo(requiredFieldsRef.top, margin = 5.dp)
//            width = Dimension.fillToConstraints
//        }
//
//        constrain(serviceRef) {
//            start.linkTo(parent.start, margin = 10.dp)
//            end.linkTo(parent.end, margin = 10.dp)
//            top.linkTo(objectsRef.top, margin = 5.dp)
//            width = Dimension.fillToConstraints
//        }
    }

    @Composable
    @ScreenPreview
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}

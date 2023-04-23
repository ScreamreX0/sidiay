package com.example.home.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum

internal interface ITicketField {
    val ticketFieldsParams: MutableState<TicketFieldParams>
    val notIntractableAlpha: Float get() = 0.8F
    val ticketData: MutableState<TicketData?> get() = mutableStateOf(null)
    val field: TicketFieldsEnum
    val ticket: MutableState<TicketEntity>
    val ticketRestrictions: TicketRestriction
    val isValueNull: Boolean

    fun init(thisField: ITicketField, ticketRestrictions: TicketRestriction, isValueNull: Boolean) {
        val ticketFieldInRequiredFields = field in ticketRestrictions.requiredFields
        val ticketFieldInAllowedFields = field in ticketRestrictions.allowedFields

        thisField.ticketFieldsParams.value = TicketFieldParams(
            starred = ticketFieldInRequiredFields,
            isClickable = ticketFieldInRequiredFields || ticketFieldInAllowedFields,
            isVisible = isValueNull && !ticketFieldInRequiredFields && !ticketFieldInAllowedFields,
        )
    }

    @Composable
    fun TicketFieldComponent(
        title: String,
        icon: Int,
        item: @Composable () -> Unit,
        ticketFieldsParams: TicketFieldParams
    ) {
        val modifier = if (ticketFieldsParams.isClickable) Modifier else Modifier.alpha(0.6F)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Icon(
                modifier = modifier
                    .height(45.dp)
                    .width(45.dp)
                    .padding(end = 10.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
            Column {
                Row {
                    Text(
                        modifier = modifier.padding(bottom = 5.dp),
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = MaterialTheme.typography.h3.fontSize
                    )
                    if (ticketFieldsParams.starred) {
                        Text(
                            modifier = modifier.padding(start = 5.dp),
                            text = "*",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = MaterialTheme.typography.h3.fontSize
                        )
                    }
                }
                item.invoke()
            }
        }
    }
}
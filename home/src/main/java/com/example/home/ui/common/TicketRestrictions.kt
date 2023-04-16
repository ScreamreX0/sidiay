package com.example.home.ui.common

import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses

class TicketRestrictions {
    companion object {
        fun getAllowedFields(
            ticketStatus: TicketStatuses?,
            executor: Boolean
        ): List<TicketFieldsEnum>? =
            when (ticketStatus) {
                TicketStatuses.NOT_FORMED ->
                    if (executor) {
                        null
                    } else {
                        listOf(
                            TicketFieldsEnum.FACILITIES,
                            TicketFieldsEnum.SERVICE,
                            TicketFieldsEnum.KIND,
                            TicketFieldsEnum.PLANE_DATE,
                            TicketFieldsEnum.PRIORITY,
                            TicketFieldsEnum.EXECUTOR,
                        )
                    }

                TicketStatuses.NEW ->
                    if (executor) {
                        listOf(TicketFieldsEnum.STATUS)
                    } else {
                        listOf(
                            TicketFieldsEnum.NAME,
                            TicketFieldsEnum.DESCRIPTION,
                            TicketFieldsEnum.FACILITIES,
                            TicketFieldsEnum.SERVICE,
                            TicketFieldsEnum.KIND,
                            TicketFieldsEnum.PLANE_DATE,
                            TicketFieldsEnum.PRIORITY,
                            TicketFieldsEnum.EXECUTOR,
                            TicketFieldsEnum.TRANSPORT,
                            TicketFieldsEnum.BRIGADE,
                        )
                    }

                TicketStatuses.ACCEPTED ->
                    if (executor) {
                        listOf(TicketFieldsEnum.STATUS)
                    } else {
                        null
                    }

                TicketStatuses.DENIED -> null
                TicketStatuses.SUSPENDED -> if (executor) {
                    listOf(TicketFieldsEnum.STATUS)
                } else {
                    null
                }

                TicketStatuses.COMPLETED -> if (executor) {
                    null
                } else {
                    listOf(TicketFieldsEnum.STATUS)
                }

                TicketStatuses.CLOSED -> null
                TicketStatuses.FOR_REVISION -> null
                null -> null
            }
    }
}
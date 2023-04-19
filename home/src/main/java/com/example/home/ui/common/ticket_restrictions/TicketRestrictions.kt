package com.example.home.ui.common.ticket_restrictions

import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses

class TicketRestrictions {
    companion object {
        fun getAllowedFields(
            selectedTicketStatus: TicketStatuses?,
            ticketStatus: TicketStatuses?,
            executor: Boolean
        ): Pair<List<TicketFieldsEnum>?, List<TicketStatuses>?> =
            when (ticketStatus) {
                TicketStatuses.NOT_FORMED ->  // Ticket status: not formed
                    if (executor) {
                        Pair(
                            null,
                            null
                        )
                    } else {
                        Pair(
                            listOf(
                                TicketFieldsEnum.FACILITIES,
                                TicketFieldsEnum.SERVICE,
                                TicketFieldsEnum.KIND,
                                TicketFieldsEnum.PLANE_DATE,
                                TicketFieldsEnum.PRIORITY,
                                TicketFieldsEnum.EXECUTOR,
                            ),
                            null
                        )
                    }

                TicketStatuses.NEW ->  // Ticket status: new
                    if (executor) {
                        Pair(
                            listOf(TicketFieldsEnum.STATUS),
                            null
                        )
                    } else {
                        Pair(
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
                            ), null
                        )
                    }

                TicketStatuses.ACCEPTED ->
                    if (executor) {
                        Pair(null, null)
                    } else {
                        Pair(null, null)
                    }

                TicketStatuses.DENIED -> if (executor) {
                    Pair(null, null)
                } else {
                    Pair(null, null)
                }

                TicketStatuses.SUSPENDED -> if (executor) {
                    Pair(null, null)
                } else {
                    Pair(null, null)
                }

                TicketStatuses.COMPLETED -> if (executor) {
                    Pair(null, null)
                } else {
                    Pair(null, null)
                }

                TicketStatuses.CLOSED -> if (executor) {
                    Pair(null, null)
                } else {
                    Pair(null, null)
                }

                TicketStatuses.FOR_REVISION -> if (executor) {
                    Pair(null, null)
                } else {
                    Pair(null, null)
                }

                null -> Pair(null, null)
            }
    }
}
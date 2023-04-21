package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses

class GetTicketRestrictionsUseCase {
    fun execute(
        selectedTicketStatus: TicketStatuses,
        ticket: TicketEntity,
        currentUser: UserEntity?,
    ): TicketRestriction {
        if (currentUser == null) {
            TODO("Offline mode")
        }

        if (ticket.executor == null) {
            TODO("Ticket has not an executor")
        }

        if (ticket.author == null) {
            TODO("Ticket has not an author")
        }

        val allowedFields = arrayListOf<TicketFieldsEnum>()
        val requiredFields = arrayListOf<TicketFieldsEnum>()
        val availableStatuses = arrayListOf<TicketStatuses>()

        when (ticket.status) {
            TicketStatuses.NOT_FORMED -> {
                when (currentUser) {
                    ticket.author -> {
                        allowedFields.addAll(
                            listOf(
                                TicketFieldsEnum.NAME,
                                TicketFieldsEnum.STATUS
                            )
                        )
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.NOT_FORMED,
                                TicketStatuses.NEW,
                                TicketStatuses.CLOSED
                            )
                        )
                        if (selectedTicketStatus == TicketStatuses.NEW) {
                            requiredFields.addAll(
                                listOf(
                                    TicketFieldsEnum.FACILITIES,
                                    TicketFieldsEnum.SERVICE,
                                    TicketFieldsEnum.KIND,
                                    TicketFieldsEnum.PRIORITY,
                                    TicketFieldsEnum.EXECUTOR,
                                    TicketFieldsEnum.PLANE_DATE
                                )
                            )
                        } else if (selectedTicketStatus == TicketStatuses.CLOSED) {
                            requiredFields.add(TicketFieldsEnum.CLOSING_DATE)
                        }
                    }
                }
            }

            TicketStatuses.NEW -> {
                when (currentUser) {
                    ticket.executor -> {
                        allowedFields.addAll(
                            listOf(
                                TicketFieldsEnum.EXECUTOR,
                                TicketFieldsEnum.STATUS
                            )
                        )
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.NEW,
                                TicketStatuses.ACCEPTED,
                                TicketStatuses.DENIED
                            )
                        )

                        if (selectedTicketStatus == TicketStatuses.DENIED) {
                            requiredFields.addAll(
                                listOf(
                                    TicketFieldsEnum.STATUS,
                                    TicketFieldsEnum.COMPLETED_WORK,
                                    TicketFieldsEnum.CLOSING_DATE
                                )
                            )
                        }
                    }

                    ticket.author -> {
                        allowedFields.addAll(
                            listOf(
                                TicketFieldsEnum.FACILITIES,
                                TicketFieldsEnum.SERVICE,
                                TicketFieldsEnum.KIND,
                                TicketFieldsEnum.PRIORITY,
                                TicketFieldsEnum.EXECUTOR,
                                TicketFieldsEnum.PLANE_DATE,
                                TicketFieldsEnum.STATUS,
                                TicketFieldsEnum.NAME,
                                TicketFieldsEnum.BRIGADE,
                                TicketFieldsEnum.TRANSPORT,
                                TicketFieldsEnum.EQUIPMENT,
                                TicketFieldsEnum.DESCRIPTION
                            )
                        )
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.NEW,
                                TicketStatuses.CLOSED
                            )
                        )
                    }

                    else -> {}
                }
            }

            TicketStatuses.ACCEPTED -> {
                when (currentUser) {
                    ticket.executor -> {
                        allowedFields.addAll(listOf(TicketFieldsEnum.STATUS))
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.ACCEPTED,
                                TicketStatuses.SUSPENDED,
                                TicketStatuses.COMPLETED
                            )
                        )
                        if (selectedTicketStatus == TicketStatuses.COMPLETED) {
                            requiredFields.addAll(
                                listOf(
                                    TicketFieldsEnum.COMPLETED_WORK,
                                    TicketFieldsEnum.CLOSING_DATE
                                )
                            )
                        }
                    }

                    ticket.author -> {
                        allowedFields.addAll(listOf(TicketFieldsEnum.STATUS))
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.ACCEPTED,
                                TicketStatuses.CLOSED
                            )
                        )
                    }

                    else -> {}
                }
            }

            TicketStatuses.SUSPENDED ->
                when (currentUser) {
                    ticket.executor -> {
                        allowedFields.add(TicketFieldsEnum.STATUS)
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.SUSPENDED, TicketStatuses.ACCEPTED
                            )
                        )
                    }

                    ticket.author -> {
                        allowedFields.add(TicketFieldsEnum.STATUS)
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.SUSPENDED, TicketStatuses.CLOSED
                            )
                        )
                    }

                    else -> {}
                }

            TicketStatuses.COMPLETED ->
                when (currentUser) {
                    ticket.author -> {
                        allowedFields.add(TicketFieldsEnum.STATUS)
                        availableStatuses.addAll(
                            listOf(
                                TicketStatuses.COMPLETED,
                                TicketStatuses.CLOSED,
                                TicketStatuses.FOR_REVISION
                            )
                        )

                        if (selectedTicketStatus == TicketStatuses.FOR_REVISION) {
                            requiredFields.add(TicketFieldsEnum.IMPROVEMENT_REASON)
                        }
                    }

                    else -> {}
                }

            TicketStatuses.DENIED -> {}

            TicketStatuses.CLOSED -> {}

            TicketStatuses.FOR_REVISION -> {}

            null -> {}
        }

        return TicketRestriction(
            allowedFields = allowedFields,
            requiredFields = requiredFields,
            availableStatuses = availableStatuses
        )
    }
}
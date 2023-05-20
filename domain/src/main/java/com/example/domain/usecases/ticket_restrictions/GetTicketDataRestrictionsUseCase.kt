package com.example.domain.usecases.ticket_restrictions

import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.DepartmentsEnum
import com.example.domain.enums.JobTitlesEnum
import com.example.domain.enums.JobTitlesEnum.*
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.TicketStatuses
import com.example.domain.enums.ui.TicketFieldsEnum
import javax.inject.Inject

class GetTicketDataRestrictionsUseCase @Inject constructor() {
    fun execute(
        ticket: TicketEntity,
        currentUser: UserEntity?,
        ticketData: TicketData?
    ): TicketData {
        ticketData ?: run {
            Logger.m("Ticket is empty. Returning empty TICKET DATA")
            return TicketData()
        }

        val currentUserJobTitle: JobTitlesEnum = currentUser?.employee?.jobTitle?.let {
            JobTitlesEnum.getByValue(it)
        } ?: run {
            Logger.m("Current user job title is empty. Returning empty TICKET DATA")
            return TicketData()
        }

        var currentTicketService: ServicesEnum? = null
        if (currentUserJobTitle != OPERATOR) {
            currentTicketService = ticket.service?.let {
                ServicesEnum.getByValue(it)
            } ?: run {
                Logger.m("Current ticket service is empty. Returning empty TICKET DATA")
                return TicketData()
            }
        }

        Logger.m("Gettings ticket data. Ticket status: ${ticket.status}, current user job title: $currentUserJobTitle ")

        when (TicketStatuses.getByValue(ticket.status)) {
            TicketStatuses.NOT_FORMED -> return when (currentUserJobTitle) {
                OPERATOR -> TicketData(
                    facilities = ticketData.facilities,
                    transport = ticketData.transport
                )

                else -> TicketData()
            }

            TicketStatuses.EVALUATED,
            TicketStatuses.SUSPENDED,
            TicketStatuses.FOR_REVISION,
            TicketStatuses.EXECUTION_PROBLEM -> return when (currentUserJobTitle) {
                SECTION_CHIEF -> TicketData(users = ticketData.users?.filter { itUser ->
                    currentTicketService!!.responsible.contains(itUser.employee?.jobTitle?.let { JobTitlesEnum.getByValue(it) })
                })

                CHIEF_ENGINEER -> TicketData(users = ticketData.users?.filter { itUser ->
                    currentTicketService!!.responsible.contains(itUser.employee?.jobTitle?.let { JobTitlesEnum.getByValue(it) })
                            && itUser.employee?.jobTitle?.let { JobTitlesEnum.getByValue(it) }?.department == DepartmentsEnum.OIL_AND_GAS_PRODUCTION
                })

                CHIEF_GEOLOGIST -> TicketData(users = ticketData.users?.filter { itUser ->
                    currentTicketService!!.responsible.contains(itUser.employee?.jobTitle?.let { JobTitlesEnum.getByValue(it) })
                            && itUser.employee?.jobTitle?.let { JobTitlesEnum.getByValue(it) }?.department == DepartmentsEnum.GEOLOGY
                })

                else -> TicketData()
            }

            TicketStatuses.COMPLETED -> return when (currentUserJobTitle) {
                SECTION_CHIEF,
                CHIEF_ENGINEER,
                CHIEF_GEOLOGIST -> TicketData(
                    users = ticketData.users?.filter { itUser ->
                        itUser.employee?.jobTitle?.let { JobTitlesEnum.getByValue(it) } == QUALITY_CONTROLLER
                    }
                )

                else -> TicketData()
            }

            else -> return TicketData()
        }
    }
}
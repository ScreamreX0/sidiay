package com.example.domain.usecases.createticket

import com.example.core.ui.utils.Constants
import com.example.domain.data_classes.params.TicketCreateParams
import com.example.domain.enums.*
import com.example.domain.repositories.IFacilityRepository
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class GetTicketCreateFieldsUseCase @Inject constructor(
    private val facilitiesRepository: IFacilityRepository,
    private val userRepository: IUserRepository
) {
    suspend fun execute() = TicketCreateParams(
        kinds = TicketKindEnum.values().toList(),
        facilities = if (Constants.DEBUG_MODE) facilitiesRepository.getTest() else facilitiesRepository.get(),
        periods = TicketPeriodEnum.values().toList(),
        priorities = TicketPriorityEnum.values().toList(),
        services = TicketServiceEnum.values().toList(),
        statuses = TicketStatusEnum.values().toList(),
        users = if (Constants.DEBUG_MODE) userRepository.getTestUsers() else userRepository.getUsers()
    )
}
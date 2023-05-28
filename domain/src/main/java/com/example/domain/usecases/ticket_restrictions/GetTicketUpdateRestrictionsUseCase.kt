package com.example.domain.usecases.ticket_restrictions

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.JobTitlesEnum
import com.example.domain.enums.JobTitlesEnum.*
import com.example.domain.enums.TicketStatuses
import com.example.domain.enums.ui.TicketFieldsEnum
import javax.inject.Inject

class GetTicketUpdateRestrictionsUseCase @Inject constructor() {
    fun execute(
        selectedTicketStatus: TicketStatuses,
        ticket: TicketEntity,
        currentUser: UserEntity?,
    ): TicketRestriction {
        val currentUserJobTitle: JobTitlesEnum = currentUser?.employee?.jobTitle?.let {
            JobTitlesEnum.getByValue(it)
        } ?: run {
            return TicketRestriction.getEmpty()
        }

        val allowedFields = arrayListOf<TicketFieldsEnum>()
        val requiredFields = arrayListOf<TicketFieldsEnum>()
        val availableStatuses = arrayListOf<TicketStatuses>()

        when (TicketStatuses.getByValue(ticket.status)) {
            TicketStatuses.NOT_FORMED -> when (currentUserJobTitle) {
                OPERATOR -> {
                    requiredFields.add(
                        TicketFieldsEnum.FACILITIES,
                        TicketFieldsEnum.TICKET_NAME,
                        TicketFieldsEnum.DESCRIPTION_OF_WORK,
                        TicketFieldsEnum.KIND,
                        TicketFieldsEnum.SERVICE
                    )
                }

                else -> {}
            }

            TicketStatuses.NEW -> when (currentUserJobTitle) {
                DISPATCHER -> {
                    requiredFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(TicketStatuses.EVALUATED)
                    if (selectedTicketStatus == TicketStatuses.EVALUATED) {
                        requiredFields.add(
                            TicketFieldsEnum.PRIORITY,
                            TicketFieldsEnum.ASSESSED_VALUE,
                            TicketFieldsEnum.ASSESSED_VALUE_DESCRIPTION,
                        )
                    }


                }

                OPERATOR -> {
                    requiredFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(TicketStatuses.CANCELED)
                    if (selectedTicketStatus == TicketStatuses.CANCELED) {
                        requiredFields.add(TicketFieldsEnum.REASON_FOR_CANCELLATION)
                    }
                }

                else -> {}
            }

            TicketStatuses.EVALUATED -> when (currentUserJobTitle) {
                SECTION_CHIEF, CHIEF_ENGINEER, CHIEF_GEOLOGIST -> {
                    requiredFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(
                        TicketStatuses.ACCEPTED,
                        TicketStatuses.SUSPENDED,
                        TicketStatuses.REJECTED,
                    )
                    when (selectedTicketStatus) {
                        TicketStatuses.ACCEPTED -> {
                            allowedFields.add(TicketFieldsEnum.PRIORITY)
                            requiredFields.add(
                                TicketFieldsEnum.EXECUTORS,
                                TicketFieldsEnum.PLANE_DATE
                            )
                        }

                        TicketStatuses.SUSPENDED -> {
                            requiredFields.add(TicketFieldsEnum.REASON_FOR_SUSPENSION)
                        }

                        TicketStatuses.REJECTED -> {
                            requiredFields.add(TicketFieldsEnum.REASON_FOR_REJECTION)
                        }

                        else -> {}
                    }
                }

                OPERATOR -> {
                    requiredFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(TicketStatuses.CANCELED)
                    when (selectedTicketStatus) {
                        TicketStatuses.CANCELED -> {
                            requiredFields.add(TicketFieldsEnum.REASON_FOR_CANCELLATION)
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            TicketStatuses.ACCEPTED -> when (currentUserJobTitle) {
                METROLOGIST, DESIGN_ENGINEER, PROCESS_ENGINEER, MECHANICAL_ENGINEER,
                ELECTRICAL_ENGINEER, WELDER, BUILDER, DESIGN_GEOLOGIST, GEOPHYSICIST,
                HYDROGEOLOGIST, LABORATORY_ASSISTANT, EXPLORATION_GEOLOGIST -> {
                    availableStatuses.add(
                        TicketStatuses.COMPLETED,
                        TicketStatuses.EXECUTION_PROBLEM
                    )
                    requiredFields.add(TicketFieldsEnum.STATUS)
                    when (selectedTicketStatus) {
                        TicketStatuses.COMPLETED -> {
                            requiredFields.add(TicketFieldsEnum.COMPLETED_WORK)
                        }

                        TicketStatuses.EXECUTION_PROBLEM -> {
                            requiredFields.add(TicketFieldsEnum.EXECUTION_PROBLEM_DESCRIPTION)
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            TicketStatuses.SUSPENDED -> when (currentUserJobTitle) {
                SECTION_CHIEF, CHIEF_ENGINEER, CHIEF_GEOLOGIST -> {
                    allowedFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(
                        TicketStatuses.ACCEPTED,
                        TicketStatuses.REJECTED,
                    )
                    when (selectedTicketStatus) {
                        TicketStatuses.ACCEPTED -> {
                            allowedFields.add(TicketFieldsEnum.PRIORITY)
                            requiredFields.add(
                                TicketFieldsEnum.EXECUTORS,
                                TicketFieldsEnum.PLANE_DATE
                            )
                        }

                        TicketStatuses.REJECTED -> {
                            requiredFields.add(TicketFieldsEnum.REASON_FOR_REJECTION)
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            TicketStatuses.FOR_REVISION,
            TicketStatuses.EXECUTION_PROBLEM -> when (currentUserJobTitle) {
                SECTION_CHIEF, CHIEF_ENGINEER, CHIEF_GEOLOGIST -> {
                    allowedFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(
                        TicketStatuses.ACCEPTED,
                        TicketStatuses.SUSPENDED,
                        TicketStatuses.REJECTED,
                    )
                    when (selectedTicketStatus) {
                        TicketStatuses.ACCEPTED -> {
                            allowedFields.add(TicketFieldsEnum.PRIORITY)
                            requiredFields.add(
                                TicketFieldsEnum.EXECUTORS,
                                TicketFieldsEnum.PLANE_DATE
                            )
                        }

                        TicketStatuses.SUSPENDED -> {
                            requiredFields.add(TicketFieldsEnum.REASON_FOR_SUSPENSION)
                        }

                        TicketStatuses.REJECTED -> {
                            requiredFields.add(TicketFieldsEnum.REASON_FOR_REJECTION)
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            TicketStatuses.COMPLETED -> when (currentUserJobTitle) {
                SECTION_CHIEF, CHIEF_ENGINEER, CHIEF_GEOLOGIST -> {
                    requiredFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(TicketStatuses.CLOSED, TicketStatuses.QUALITY_CHECKING)
                    when (selectedTicketStatus) {
                        TicketStatuses.QUALITY_CHECKING -> {
                            requiredFields.add(TicketFieldsEnum.QUALITY_CONTROLLERS)
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            TicketStatuses.QUALITY_CHECKING -> when (currentUserJobTitle) {
                QUALITY_CONTROLLER -> {
                    requiredFields.add(TicketFieldsEnum.STATUS)
                    availableStatuses.add(TicketStatuses.CLOSED, TicketStatuses.FOR_REVISION)
                    when (selectedTicketStatus) {
                        TicketStatuses.FOR_REVISION -> {
                            requiredFields.add(TicketFieldsEnum.IMPROVEMENT_COMMENT)
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            else -> {}
        }

        return TicketRestriction(
            allowedFields = allowedFields,
            requiredFields = requiredFields,
            availableStatuses = availableStatuses
        )
    }

    companion object {
        private fun <E> ArrayList<E>.add(vararg elements: E) {
            this.addAll(elements)
        }
    }
}
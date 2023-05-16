package com.example.domain.enums

enum class JobTitlesEnum(val value: Int, val department: DepartmentsEnum?) {
    SECTION_CHIEF(value = 1, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    CHIEF_ENGINEER(value = 18, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    DISPATCHER(value = 2, department = null),
    OPERATOR(value = 3, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    DESIGN_ENGINEER(value = 4, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    PROCESS_ENGINEER(value = 5, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    MECHANICAL_ENGINEER(value = 6, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    ELECTRICAL_ENGINEER(value = 7, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    QUALITY_CONTROLLER(value = 8, department = null),
    METROLOGIST(value = 9, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    WELDER(value = 10, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    BUILDER(value = 11, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION),
    CHIEF_GEOLOGIST(value = 19, department = DepartmentsEnum.GEOLOGY),
    DESIGN_GEOLOGIST(value = 12, department = DepartmentsEnum.GEOLOGY),
    GEOPHYSICIST(value = 13, department = DepartmentsEnum.GEOLOGY),
    HYDROGEOLOGIST(value = 14, department = DepartmentsEnum.GEOLOGY),
    LABORATORY_ASSISTANT(value = 15, department = DepartmentsEnum.GEOLOGY),
    EXPLORATION_GEOLOGIST(value = 16, department = DepartmentsEnum.GEOLOGY);

    companion object {
        fun getByValue(value: Int): JobTitlesEnum? = values().find { it.value == value }
    }
}
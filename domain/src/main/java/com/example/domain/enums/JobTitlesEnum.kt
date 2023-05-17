package com.example.domain.enums

enum class JobTitlesEnum(val value: Int, val department: DepartmentsEnum?, val label: String) {
    SECTION_CHIEF(value = 1, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Начальник учатска"),
    CHIEF_ENGINEER(value = 18, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Главный инженер"),
    DISPATCHER(value = 2, department = null, label = "Диспетчер"),
    OPERATOR(value = 3, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Оператор"),
    DESIGN_ENGINEER(value = 4, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Инженер-проектировщик"),
    PROCESS_ENGINEER(value = 5, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Инженер-технолог"),
    MECHANICAL_ENGINEER(value = 6, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Инженер-механик"),
    ELECTRICAL_ENGINEER(value = 7, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Инженер-электрик"),
    QUALITY_CONTROLLER(value = 8, department = null, label = "Специалист по контролю качества"),
    METROLOGIST(value = 9, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Метролог"),
    WELDER(value = 10, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Сварщик"),
    BUILDER(value = 11, department = DepartmentsEnum.OIL_AND_GAS_PRODUCTION, label = "Строитель"),
    CHIEF_GEOLOGIST(value = 19, department = DepartmentsEnum.GEOLOGY, label = "Главный геолог"),
    DESIGN_GEOLOGIST(value = 12, department = DepartmentsEnum.GEOLOGY, label = "Геолог-проектировщик"),
    GEOPHYSICIST(value = 13, department = DepartmentsEnum.GEOLOGY, label = "Геофизик"),
    HYDROGEOLOGIST(value = 14, department = DepartmentsEnum.GEOLOGY, label = "Гидрогеолог"),
    LABORATORY_ASSISTANT(value = 15, department = DepartmentsEnum.GEOLOGY, label = "Лаборант"),
    EXPLORATION_GEOLOGIST(value = 16, department = DepartmentsEnum.GEOLOGY, label = "Геолог-разведчик");

    companion object {
        fun getByValue(value: Int): JobTitlesEnum? = values().find { it.value == value }
    }
}
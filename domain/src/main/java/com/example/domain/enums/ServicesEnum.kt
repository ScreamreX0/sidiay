package com.example.domain.enums

enum class ServicesEnum(val value: Int, val label: String, val responsible: Array<JobTitlesEnum>) {
    // Насосно-перекачивающее оборудование
    NPO(1, "НПО", arrayOf(JobTitlesEnum.MECHANICAL_ENGINEER)),

    // Работа с энергетическими системами
    ENERGO(2, "Энерго", arrayOf(JobTitlesEnum.ELECTRICAL_ENGINEER)),

    // контрольно-измерительные приборы
    KIP(3, "КИП", arrayOf(JobTitlesEnum.METROLOGIST)),

    // Сварочные работы
    WELDING(4, "Сварочные работы",arrayOf(JobTitlesEnum.WELDER)),

    // Подготовка и разведка скважин
    PRS(5, "ПРС", arrayOf(JobTitlesEnum.EXPLORATION_GEOLOGIST)),

    // Исследование
    RESEARCH(6, "Исследование", arrayOf(JobTitlesEnum.EXPLORATION_GEOLOGIST)),

    // Строительные работы
    CONSTRUCTION_WORKS(7, "Строительные работы", arrayOf(JobTitlesEnum.BUILDER));

    companion object {
        fun getByValue(value: Int): ServicesEnum? = values().find { it.value == value }
    }
}
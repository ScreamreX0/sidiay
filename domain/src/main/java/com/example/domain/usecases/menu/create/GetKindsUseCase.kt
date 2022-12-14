package com.example.domain.usecases.menu.create

import com.example.domain.enums.Kinds
import javax.inject.Inject

class GetKindsUseCase @Inject constructor() {
    fun execute(): List<Kinds> {
        return Kinds.values().toList()
    }
}
package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticketstates.KindState
import javax.inject.Inject

class GetKindsUseCase @Inject constructor() {
    fun execute(): List<KindState> {
        return KindState.values().toList()
    }
}
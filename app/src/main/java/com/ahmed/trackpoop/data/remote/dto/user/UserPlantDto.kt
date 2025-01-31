package com.ahmed.trackpoop.data.remote.dto.user

import java.time.LocalDate

data class WaterReminderDto(
    val lastTimeWatered: LocalDate? = null,
    val waterAmount: Double? = null,
    val waterFrequency: Int? = null
)

data class FertilizeReminderDto(
    val lastTimeFertilized: LocalDate? = null,
    val fertilizeAmount: Double? = null,
    val fertilizeFrequency: Int? = null
)

data class AddUserPlantDto(
    val plantId: String,
    val waterReminder: WaterReminderDto? = null,
    val fertilizeReminder: FertilizeReminderDto? = null
)
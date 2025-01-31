package com.ahmed.trackpoop.core.data

import com.ahmed.trackpoop.domain.model.*
import java.time.LocalDate


val sampleWaterReminder = WaterReminder(
    lastTimeWatered = LocalDate.now(),
    waterAmount = 250,
    waterFrequency = 7
)

val sampleFertilizeReminder = FertilizeReminder(
    lastTimeFertilized = LocalDate.now().minusDays(1),
    fertilizeAmount = 50,
    fertilizeFrequency = 30
)

val sampleUserPlant = UserPlant(
    plant = samplePlant,
    waterReminder = sampleWaterReminder,
    fertilizeReminder = sampleFertilizeReminder
)

val sampleUser = User(
    _id = "u123",
    name = "John Doe",
    email = "john.doe@example.com",
    phone = "+1234567890",
    badge = Badge.ENTHUSIAST,
    score = 150,
    myPlants = listOf(sampleUserPlant),
    password = "hashedPassword123",
    role = Role.USER,
    otpCode = null,
    codeExpires = null,
    isVerified = true,
)


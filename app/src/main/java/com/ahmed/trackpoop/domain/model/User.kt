package com.ahmed.trackpoop.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

enum class Role {
    ADMIN,
    USER
}

enum class Badge {
    BEGINNER,
    ENTHUSIAST,
    EXPERT,
    MASTER
}

enum class ReminderType {
    WATER,
    FERTILIZER
}

data class WaterReminder(
    val lastTimeWatered: LocalDate?,  // Change back to LocalDate
    val waterAmount: Int?,
    val waterFrequency: Int?
) {
    val daysUntilNextWatering: String?
        get() {
            if (lastTimeWatered == null || waterFrequency == null || waterAmount == null) return null

            val today = LocalDate.now()  // Get today's date

            if (today == lastTimeWatered)
                return "Today"

            val nextWateringDate =
                lastTimeWatered.plusDays(waterFrequency.toLong())  // Calculate next watering date
            val daysLeft =
                today.until(nextWateringDate).days  // Get the number of days until next watering

            return when {
                daysLeft == 0 -> "Today"  // If it's today
                daysLeft > 0 -> "In $daysLeft days"  // If in the future
                else -> "Overdue by ${-daysLeft} days"  // If overdue
            }
        }
}

data class FertilizeReminder(
    val lastTimeFertilized: LocalDate?,  // Change back to LocalDate
    val fertilizeAmount: Int?,
    val fertilizeFrequency: Int?
) {
    val daysUntilNextFertilizing: String?
        get() {
            if (lastTimeFertilized == null || fertilizeFrequency == null || fertilizeAmount == null) return null

            val today = LocalDate.now()  // Get today's date

            if (today == lastTimeFertilized)
                return "Today"

            val nextFertilizingDate =
                lastTimeFertilized.plusDays(fertilizeFrequency.toLong())  // Calculate next fertilizing date
            val daysLeft =
                today.until(nextFertilizingDate).days  // Get the number of days until next fertilizing

            return when {
                daysLeft == 0 -> "Today"  // If it's today
                daysLeft > 0 -> "In $daysLeft days"  // If in the future
                else -> "Overdue by ${-daysLeft} days"  // If overdue
            }
        }
}


data class UserPlant(
    val plant: Plant,
    val waterReminder: WaterReminder,
    val fertilizeReminder: FertilizeReminder
) {
    val hasReminderInWater: Boolean
        get() = waterReminder.daysUntilNextWatering != null && waterReminder.waterAmount != null && waterReminder.waterFrequency != null
    val hasReminderInFertilize: Boolean
        get() = fertilizeReminder.daysUntilNextFertilizing != null && fertilizeReminder.fertilizeAmount != null && fertilizeReminder.fertilizeFrequency != null
    val isWateredTodayOrOverdue: Boolean
        get() = waterReminder.lastTimeWatered == LocalDate.now() || isWaterOverdue
    val isFertilizedTodayOrOverdue: Boolean
        get() = fertilizeReminder.lastTimeFertilized == LocalDate.now() || isFertilizedOverdue

    val isWateredToday: Boolean
        get() = waterReminder.lastTimeWatered == LocalDate.now()

    val isFertilizedToday: Boolean
        get() = fertilizeReminder.lastTimeFertilized == LocalDate.now()

    val isWateredInFuture: Boolean
        get() = waterReminder.daysUntilNextWatering != null && waterReminder.daysUntilNextWatering != "Today" && !isWaterOverdue

    val isFertilizedInFuture: Boolean
        get() = fertilizeReminder.daysUntilNextFertilizing != null && fertilizeReminder.daysUntilNextFertilizing != "Today" && !isFertilizedOverdue

    val isWaterOverdue: Boolean
        get() = waterReminder.daysUntilNextWatering != null && waterReminder.daysUntilNextWatering!!.startsWith(
            "Overdue"
        )

    val isFertilizedOverdue: Boolean
        get() = fertilizeReminder.daysUntilNextFertilizing != null && fertilizeReminder.daysUntilNextFertilizing!!.startsWith(
            "Overdue"
        )

}

data class User(
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val image: String = "",
    val badge: Badge = Badge.BEGINNER,
    val score: Int,
    val myPlants: List<UserPlant> = emptyList(),
    val password: String,
    val role: Role,
    val otpCode: String?,
    val codeExpires: LocalDateTime?,
    val isVerified: Boolean,
)

data class  UserComment(
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val image: String = "",
    val badge: Badge = Badge.BEGINNER,
    val score: Int,
)

val emptyUser = User(
    _id = "",
    name = "",
    email = "",
    phone = "",
    badge = Badge.BEGINNER,
    score = 0,
    myPlants = emptyList(),
    password = "",
    role = Role.USER,
    otpCode = null,
    codeExpires = null,
    isVerified = false,
)


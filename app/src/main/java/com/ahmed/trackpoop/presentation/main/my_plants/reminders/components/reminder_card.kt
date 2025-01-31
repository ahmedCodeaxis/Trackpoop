package com.ahmed.trackpoop.presentation.main.my_plants.reminders.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.data.sampleUserPlant
import com.ahmed.trackpoop.di.SERVER_URL
import com.ahmed.trackpoop.domain.model.Plant
import com.ahmed.trackpoop.domain.model.ReminderType
import com.ahmed.trackpoop.domain.model.UserPlant
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

private object ReminderCardDefaults {
    val ActiveColor = Color(0xFF129C52)
    val InactiveColor = Color(0xFFAAAAAA)
    val ActiveTextColor = Color(0xFF129C52)
    val InactiveTextColor = Color(0xFF737373)
    val ActiveBackgroundColor = Color(0xFFD2EFE0)
    val InactiveBackgroundColor = Color(0xFFF5F5F5)
}

@Composable
fun ReminderCard(
    reminder: UserPlant,
    type: ReminderType,
    onUpdateReminder: (UserPlant, ReminderType) -> Unit = { _, _ -> }
) {
    val reminderState = getReminderState(reminder, type)

    Box(
        modifier = Modifier
            .height(102.dp)
            .background(Color.White, RoundedCornerShape(4.dp))
            .border(
                2.dp,
                if (reminderState.isActive) ReminderCardDefaults.ActiveColor else ReminderCardDefaults.InactiveColor,
                RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PlantImage(reminder.plant)

                ReminderContent(
                    plantName = reminder.plant.name,
                    reminderState = reminderState,
                    type = type
                )
            }

            Row {
                ReminderStatusIcon(
                    reminderState = reminderState,
                    type = type,
                    reminder = reminder,  // Pass the reminder parameter
                    onUpdateReminder = onUpdateReminder
                )

                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Composable
private fun ReminderInfo(
    amount: String,
    iconRes: Int,
    contentDescription: String? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = amount,
            color = ReminderCardDefaults.InactiveTextColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun PlantImage(plant: Plant) {
    Image(
        painter = rememberAsyncImagePainter(SERVER_URL + plant.image),
        contentDescription = plant.name,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .padding(2.dp)
            .size(93.dp, 98.dp)
    )
}

@Composable
private fun ReminderContent(
    plantName: String,
    reminderState: ReminderState,
    type: ReminderType
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = plantName,
            color = Color(0xFF262626),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        ReminderInfo(
            amount = reminderState.amount,
            iconRes = if (type == ReminderType.FERTILIZER) R.drawable.fertilizer else R.drawable.droplets
        )

        ScheduleInfo(
            scheduleText = reminderState.scheduleText,
            textColor = if (reminderState.isActive) ReminderCardDefaults.ActiveTextColor else ReminderCardDefaults.InactiveTextColor
        )
    }
}

@Composable
private fun ScheduleInfo(scheduleText: String, textColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.calendar_icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = scheduleText,
            color = textColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}

@Composable
private fun ReminderStatusIcon(
    reminderState: ReminderState,
    type: ReminderType,
    reminder: UserPlant,  // Add reminder parameter
    onUpdateReminder: (UserPlant, ReminderType) -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                if (reminderState.isActive) ReminderCardDefaults.ActiveBackgroundColor
                else ReminderCardDefaults.InactiveBackgroundColor,
                RoundedCornerShape(50.dp)
            )
            .border(
                1.dp,
                if (reminderState.isActive) ReminderCardDefaults.ActiveColor
                else ReminderCardDefaults.InactiveColor,
                RoundedCornerShape(50.dp)
            )
            .padding(14.dp)
            .clickable { onUpdateReminder(reminder, type) }
    ) {
        if (reminderState.isActive)
            Image(
                painter = painterResource(
                    id = R.drawable.check_icon
                ),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        else
            Image(
                painter = painterResource(
                    id = if (type == ReminderType.FERTILIZER)
                        R.drawable.fertilizer_fill
                    else R.drawable.droplets_fill
                ),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )


    }
}

private data class ReminderState(
    val isActive: Boolean,
    val amount: String,
    val scheduleText: String
)

private fun getReminderState(reminder: UserPlant, type: ReminderType): ReminderState {
    val isActive = if (type == ReminderType.WATER)
        reminder.isWateredToday
    else reminder.isFertilizedToday

    val amount = if (type == ReminderType.WATER)
        "${reminder.waterReminder.waterAmount} ml"
    else "${reminder.fertilizeReminder.fertilizeAmount} g"

    val scheduleText = (if (type == ReminderType.WATER)
        reminder.waterReminder.daysUntilNextWatering
    else reminder.fertilizeReminder.daysUntilNextFertilizing) ?: ""

    return ReminderState(isActive, amount, scheduleText)
}

@Preview
@Composable
fun PreviewReminderCard() {

    TrackPoopTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ReminderCard(
                reminder = sampleUserPlant,
                type = ReminderType.WATER
            )

            ReminderCard(
                reminder = sampleUserPlant,
                type = ReminderType.FERTILIZER
            )
        }
    }
}
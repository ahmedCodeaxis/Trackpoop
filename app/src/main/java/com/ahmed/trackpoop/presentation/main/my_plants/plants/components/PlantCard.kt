package com.ahmed.trackpoop.presentation.main.my_plants.plants.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.data.sampleUserPlant
import com.ahmed.trackpoop.di.SERVER_URL
import com.ahmed.trackpoop.domain.model.UserPlant

@Composable
fun PlantCard(
    plant: UserPlant,
    onClick: () -> Unit = {}  // Add onClick parameter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),  // Add clickable modifier
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(SERVER_URL + plant.plant.image),
                    contentDescription = "Plant Image",
                    modifier = Modifier
                        .size(60.dp, 80.dp)
                        .background(Color.LightGray, RoundedCornerShape(5.dp))
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = plant.plant.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = plant.plant.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (plant.hasReminderInWater) Color(0xFFDCE1F3) else Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.WaterDrop,
                            contentDescription = "Icon Water",
                            tint = if (plant.hasReminderInWater) Color(0xFF2E4185) else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    if (plant.hasReminderInWater)
                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = "${plant.waterReminder.waterAmount} ml",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Text(
                                text = plant.waterReminder.daysUntilNextWatering ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                }

                Row {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (plant.hasReminderInFertilize) Color(0xFFDCF3E7) else Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.fertilizer),
                            contentDescription = "Icon Sun",
                            tint = if (plant.hasReminderInFertilize) Color(0xFF2E8540) else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    if (plant.hasReminderInFertilize)
                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = "${plant.fertilizeReminder.fertilizeAmount} g",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Text(
                                text = plant.fertilizeReminder.daysUntilNextFertilizing ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantCardPreview() {
    PlantCard(
        plant = sampleUserPlant
    )
}
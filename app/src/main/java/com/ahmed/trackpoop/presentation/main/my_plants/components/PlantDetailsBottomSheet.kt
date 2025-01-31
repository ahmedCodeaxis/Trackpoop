package com.ahmed.trackpoop.presentation.main.my_plants.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ahmed.trackpoop.domain.model.ReminderType
import com.ahmed.trackpoop.domain.model.UserPlant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantDetailsBottomSheet(
    plant: UserPlant,
    onDismiss: () -> Unit,
    onSetReminder: (String, ReminderType, Int, Double) -> Unit
) {
    var selectedReminderType by remember { mutableStateOf<ReminderType?>(null) }
    var frequency by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = plant.plant.name,
                style = MaterialTheme.typography.headlineSmall
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Set Reminder",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { 
                        selectedReminderType = ReminderType.WATER
                        // Reset inputs when switching type
                        frequency = ""
                        amount = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedReminderType == ReminderType.WATER) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Water")
                }
                Button(
                    onClick = { 
                        selectedReminderType = ReminderType.FERTILIZER
                        // Reset inputs when switching type
                        frequency = ""
                        amount = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedReminderType == ReminderType.FERTILIZER) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Fertilizer")
                }
            }
            
            if (selectedReminderType != null) {
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = frequency,
                    onValueChange = { frequency = it },
                    label = { Text("Frequency (days)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { 
                        Text(
                            "Amount (${if (selectedReminderType == ReminderType.WATER) "ml" else "g"})"
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                var showError by remember { mutableStateOf(false) }
                var errorMessage by remember { mutableStateOf("") }
                
                if (showError) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                
                Button(
                    onClick = {
                        try {
                            val freq = frequency.toIntOrNull()
                            val amt = amount.toDoubleOrNull()
                            
                            when {
                                freq == null -> {
                                    showError = true
                                    errorMessage = "Please enter a valid frequency"
                                }
                                amt == null -> {
                                    showError = true
                                    errorMessage = "Please enter a valid amount"
                                }
                                else -> {
                                    onSetReminder(plant.plant._id, selectedReminderType!!, freq, amt)
                                    onDismiss()
                                }
                            }
                        } catch (e: Exception) {
                            showError = true
                            errorMessage = "An error occurred: ${e.message}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = frequency.isNotEmpty() && amount.isNotEmpty()
                ) {
                    Text("Set Reminder")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

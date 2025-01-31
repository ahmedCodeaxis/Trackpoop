package com.ahmed.trackpoop.presentation.community.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.navigation.Screen

@Composable
fun ChatScreen(navController: NavController, paddingValues: PaddingValues = PaddingValues(0.dp)) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp) // Add padding to avoid overlap with input field
        ) {
            TopBar(navController)
            Message()
        }
        MessageInput(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            navController.navigate(Screen.ChatsScreen.route)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user), // Replace with a back icon
                contentDescription = "Back",
                tint = Color(0xFF00BFA5)
            )
        }
        Text(text = "Messages", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { /* Add action if needed */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user), // Replace with an appropriate icon
                contentDescription = "Lock",
                tint = Color(0xFF00BFA5)
            )
        }
    }
}

@Composable
fun Message() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.weight(1f)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Example messages, replace with real data
        MessageItem("Hello!", isUserMessage = false)
        MessageItem("Hi there! How are you?", isUserMessage = true)
        MessageItem("I'm doing well, thank you!", isUserMessage = false)
        MessageItem("Great to hear!", isUserMessage = true)
    }
}

@Composable
fun MessageItem(text: String, isUserMessage: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isUserMessage) Color(0xFFDCF8C6) else Color(0xFFE5E5EA),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text(text = text, color = Color.Black, fontSize = 16.sp)
        }
    }
}

@Composable
fun MessageInput(modifier: Modifier = Modifier) {
    val messageText = remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText.value,
            onValueChange = { messageText.value = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = "Type a message...") },
           /* colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF1F1F1),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )*/
        )
        IconButton(onClick = {
            // Add action to send the message
            messageText.value = "" // Clear the input field after sending
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_email), // Replace with a send icon
                contentDescription = "Send",
                tint = Color(0xFF00BFA5)
            )
        }
    }
}

@Preview
@Composable
fun PreviewPrivateChat() {
    val navController = rememberNavController()
    ChatScreen(navController)
}

package com.ahmed.trackpoop.presentation.main.gemini


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.ui.theme.ColorModelMessage
import com.ahmed.trackpoop.ui.theme.ColorUserMessage
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

import com.ahmed.trackpoop.ui.theme.Purple80

@Composable
fun GeminiChatScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: GeminiChatViewModel = koinViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppHeader(paddingValues, navController)
            MessageList(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                messageList = viewModel.messageList
            )
            MessageInput(
                onMessageSend = {
                    viewModel.sendMessage(it)
                }
            )
        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<GeminiChatState>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.chat_icon),
                contentDescription = "Icon",
                tint = Purple80,
            )
            Text(text = "Ask me anything", fontSize = 22.sp)
        }
    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: GeminiChatState) {
    val isModel = messageModel.role == "model"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isModel) Arrangement.Start else Arrangement.End
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 340.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = true
                ),
            shape = RoundedCornerShape(
                topStart = if (isModel) 4.dp else 16.dp,
                topEnd = if (isModel) 16.dp else 4.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isModel) ColorModelMessage else ColorUserMessage
            )
        ) {
            SelectionContainer {
                Text(
                    text = messageModel.message,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .padding(bottom = 76.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            value = message,
            onValueChange = { message = it },
            placeholder = { Text("Type a message...") },
            shape = RoundedCornerShape(20.dp),
            maxLines = 4
        )
        IconButton(
            onClick = {
                if (message.isNotEmpty()) {
                    onMessageSend(message)
                    message = ""
                }
            },
            modifier = Modifier
                .shadow(4.dp, CircleShape)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Color.White
            )
        }
    }
}

@Composable
fun AppHeader(paddingValues: PaddingValues, navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(paddingValues)
            .padding(horizontal = 8.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = "Back Icon",
            modifier = Modifier
                .size(32.dp)
                .clickable(onClick = { navController.popBackStack() }),
            tint = Color.White
        )

        Text(
            text = "AgroAi Bot",
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArScreen() {
    TrackPoopTheme {
        GeminiChatScreen(navController = rememberNavController())
    }
}
















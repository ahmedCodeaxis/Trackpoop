package com.ahmed.trackpoop.presentation.community.chats


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ChatsScreen(navController: NavController, paddingValues: PaddingValues = PaddingValues(0.dp)) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        TopBar()

        SearchBar()

        // Horizontal contacts row
        HorizontalContacts()

        // Vertical list of messages
        MessageList()
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Filters", color = Color(0xFF47B077), fontSize = 16.sp)
        Text(text = "Messages", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { /* Add action here */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null,
                tint = Color(0xFF00BFA5)
            )
        }
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Search") },

        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null,
                tint = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = CircleShape

    )
}

@Composable
fun HorizontalContacts() {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(5) {
            ContactItem()
        }
    }
}

@Composable
fun ContactItem() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFF47B077), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // contact image
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "Contact Name",
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun MessageList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp)
    ) {
        items(8) {
            MessageItem()
        }
    }
}

@Composable
fun MessageItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFF47B077), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // contact image
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Contact Name",
// Body/Bold
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    // fontFamily = FontFamily(Font(R.font.sf_pro_text)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF47B077),
                )
            )
            Text(
                text = "Message preview...",
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
        Text(
            text = "9:41 AM",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}


@Preview
@Composable
fun ChatsScreen() {
    val navController = rememberNavController()
    ChatsScreen(navController)
}
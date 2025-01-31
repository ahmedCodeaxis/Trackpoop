package com.ahmed.trackpoop.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.di.SERVER_URL
import com.ahmed.trackpoop.navigation.Screen

@Composable
fun Header(
    navController: NavController,
    title: String = "Hello Habib",
    subTitle: String = "Welcome back!",
    userImageURL: String = SERVER_URL + "/uploads/defaultUser.jpg",
    content: @Composable (() -> Unit)? = null // Allow passing dynamic content
) {
    val custMod = content?.let { Modifier.fillMaxWidth() } ?: Modifier.fillMaxWidth().fillMaxHeight()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(content.let { if (it != null) 150.dp else 100.dp })
    ) {
        Image(
            painter = painterResource(id = R.drawable.headerbg), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = custMod,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        subTitle, style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF676C6B),
                        )
                    )

                    Text(
                        title, style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF3D4743),
                        )
                    )
                }
                Image(
                    painter = rememberAsyncImagePainter(userImageURL),
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)  // Add this modifier to make the image circular
                        .clickable(onClick = { navController.navigate(Screen.ProfileScreen.route) })
                )
            }

            content?.invoke() // Dynamically display the passed composable content
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(
        navController = rememberNavController(),
        content = {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                placeholder = { Text("Rechercher des plantes", color = Color(0xFFC8C8C8)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White.copy(alpha = 0.9f), // 80% opacity
                    focusedContainerColor = Color.White.copy(alpha = 0.9f), // 80% opacity
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorContainerColor = Color.White.copy(alpha = 0.9f), // 80% opacity
                    disabledContainerColor = Color.White.copy(alpha = 0.9f), // 80% opacity
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.medium // Apply border radius here
            )
        }
    )
}

package com.ahmed.trackpoop.presentation.post.post.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.data.sampleUser
import com.ahmed.trackpoop.domain.model.User

@Composable
fun UserList(users: List<User>) {
    LazyRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users.size) { index ->
            UserListItem(
                profileImage = painterResource(id = R.drawable.user_profile),
                name = users[index].name
            )
        }
    }
}

@Composable
fun UserListItem(profileImage: Painter, name: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = profileImage,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )
        Text(
            text = name,
            modifier = Modifier.padding(top = 8.dp),
            fontWeight = FontWeight.Medium,
            fontSize = 8.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserList() {
    val users = List(10) {
        sampleUser
    }
    UserList(users = users)
}
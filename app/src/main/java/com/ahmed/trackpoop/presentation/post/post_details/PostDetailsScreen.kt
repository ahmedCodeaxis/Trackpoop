package com.ahmed.trackpoop.presentation.post.post_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.di.SERVER_URL

import com.ahmed.trackpoop.presentation.post.post.components.PostCard

@Composable
fun PostDetailsScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    postDetailsViewModel: PostDetailsViewModel = koinViewModel()
) {
    val state = postDetailsViewModel.state

    if (state.post != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .systemBarsPadding() // This will add padding for system bars
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp) // Add vertical padding
                    .padding(bottom = 90.dp), // Extra padding for comment input
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    // Post Content with elevation
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        tonalElevation = 1.dp,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        PostCard(
                            profileImage = rememberAsyncImagePainter(SERVER_URL + state.post.user.image),
                            username = state.post.user.name,
                            postImage = rememberAsyncImagePainter(SERVER_URL + state.post.image),
                            caption = state.post.content,
                            likes = state.post.likes.size,
                            isLiked = state.isLiked,
                            onLikeClicked = { postDetailsViewModel.likePost() },
                            onCommentClicked = {}
                        )
                    }
                }

                item {
                    Text(
                        text = "Comments",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(state.post.comments.size) { index ->
                    val comment = state.post.comments[index]
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        tonalElevation = 0.5.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = comment.user.name,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = comment.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Fixed comment input section at bottom
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Add bottom padding
                tonalElevation = 2.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = state.comment,
                        onValueChange = { postDetailsViewModel.onCommentChange(it) },
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                Color(0xFFF5F5F5), // Light gray background
                                RoundedCornerShape(24.dp)
                            ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { 
                            Text(
                                "Add a comment...",
                                color = Color.Gray
                            ) 
                        },
                        singleLine = true
                    )
                    
                    Button(
                        onClick = { postDetailsViewModel.addComment(state.comment) },
                        enabled = state.comment.isNotBlank(),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(
                            "Post",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPostDetailsScreen() {
    PostDetailsScreen(navController = rememberNavController())
}
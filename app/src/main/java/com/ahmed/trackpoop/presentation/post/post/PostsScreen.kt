package com.ahmed.trackpoop.presentation.post.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.ahmed.trackpoop.navigation.Screen


// Add these imports
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import com.ahmed.trackpoop.presentation.post.post.components.AddPostBottomSheet
import com.ahmed.trackpoop.presentation.post.post.components.EmptyPosts
import com.ahmed.trackpoop.presentation.post.post.components.PostCard
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostsScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    postsViewModel: PostsViewModel = koinViewModel()
) {
    val state = postsViewModel.state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { postsViewModel.refresh() }
    )

    if (state.showAddPost) {
        AddPostBottomSheet(
            onDismiss = { postsViewModel.toggleAddPost(false) },
            onSubmit = { caption, uri ->
                postsViewModel.createPost(caption, uri)
            },
            isLoading = state.isLoading,
            postCreated = state.postCreated,
            onPostCreated = { postsViewModel.resetPostCreated() }
        )
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.weather),
                contentDescription = "Weather",
                modifier = Modifier
                    .size(46.dp)
                    .clickable { navController.navigate(Screen.WeatherScreen.route) }
            )

            Text(
                text = "Discover",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Box {
                // Profile image
//                Image(
//                    painter = rememberAsyncImagePainter(SERVER_URL + state.user?.image),
//                    contentDescription = "Profile",
//                    modifier = Modifier
//                        .size(46.dp)
//                        .clip(CircleShape)
//                        .clickable { navController.navigate(Screen.ProfileScreen.route) }
//                )

                // Add post FAB
                FloatingActionButton(
                    onClick = { postsViewModel.toggleAddPost(true) },
                    modifier = Modifier
                        .size(46.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-8).dp, y = (-8).dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add post",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            when {
                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {
                    if (state.posts.isEmpty()) {
                        EmptyPosts(onAddPostClick = { postsViewModel.toggleAddPost(true) })
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(top = 16.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(state.posts.size) { index ->
                                val isLiked = state.posts[index].likes.contains(state.userId)
                                PostCard(
                                    profileImage = rememberAsyncImagePainter(SERVER_URL + state.posts[index].user.image),
                                    username = state.posts[index].user.name,
                                    postImage = rememberAsyncImagePainter(SERVER_URL + state.posts[index].image),
                                    caption = state.posts[index].content,
                                    likes = state.posts[index].likes.size,
                                    isLiked = isLiked,
                                    onLikeClicked = {
                                        postsViewModel.likePost(
                                            state.posts[index]._id,
                                            isLiked
                                        )
                                    },
                                    onCommentClicked = {
                                        navController.navigate(Screen.PostDetailsScreen.route + "/${state.posts[index]._id}")
                                    }
                                )
                                if (index < state.posts.size - 1) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPostScreen() {
    TrackPoopTheme  {
        PostsScreen(
            navController = rememberNavController()
        )
    }
}
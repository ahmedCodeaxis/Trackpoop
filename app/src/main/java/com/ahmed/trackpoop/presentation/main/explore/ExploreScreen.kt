package com.ahmed.trackpoop.presentation.main.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.di.SERVER_URL
import com.ahmed.trackpoop.domain.model.Plant
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

@Composable
fun ExploreScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        TopBar(navController)
        TabSection(
            selectedTab = state.selectedTab,
            onTabSelected = viewModel::onTabSelected
        )
        SearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = viewModel::onSearchQueryChange
        )
        PlantsList(
            isLoading = state.isLoading,
            error = state.error,
            plants = state.plants,
            viewModel,
            state
        )
    }
}

@Composable
private fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Explore",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { navController.navigate(Screen.GeminiChatScreen.route) }
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bot_icon), // Replace with your AI icon resource
                contentDescription = "Ask AI",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ask AI",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun TabSection(
    selectedTab: ExploreTab,
    onTabSelected: (ExploreTab) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabItem(
            title = "Plants",
            isSelected = selectedTab == ExploreTab.Plants,
            onClick = { onTabSelected(ExploreTab.Plants) }
        )
        TabItem(
            title = "Trees",
            isSelected = selectedTab == ExploreTab.Trees,
            onClick = { onTabSelected(ExploreTab.Trees) }
        )
    }
}

@Composable
private fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = title,
            color = if (isSelected) Color(0xFF129C52) else Color(0xFF737373),
            fontSize = 14.sp
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(57.dp)
                    .height(1.dp)
                    .background(Color(0xFF129C52))
            )
        }
    }
}

@Composable
private fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search plants...", color = Color(0xFFA3A3A3)) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search",
                    modifier = Modifier.size(24.dp)
                )
            },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = MaterialTheme.colorScheme.primary,
//                unfocusedBorderColor = Color(0xFFE0E0E0)
//            ),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlantsList(
    isLoading: Boolean,
    error: String,
    plants: List<Plant>,
    viewModel: ExploreViewModel,
    state: ExploreState
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.refreshPlants() }
    )

    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ) {
        when {
            error.isNotEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error,
                        color = Color.Red
                    )
                }
            }

            plants.isEmpty() && !isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No plants found")
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(plants) { plant ->
                        PlantItem(
                            id = plant._id,
                            name = plant.name,
                            scientificName = plant.scientificName,
                            family = plant.family,
                            imageRes = SERVER_URL + plant.image,
                            isAdded = state.userPlants.filter {
                                it.plant._id == plant._id
                            }.isNotEmpty(),
                            viewModel
                        )
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun PlantItem(
    id: String,
    name: String,
    scientificName: String,
    family: String,
    imageRes: String,
    isAdded: Boolean,
    viewModel: ExploreViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(imageRes),
                contentDescription = name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = scientificName,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF737373)
                )
                Text(
                    text = family,
                    fontSize = 12.sp,
                    color = Color(0xFF737373)
                )
            }
        }
        if (!isAdded)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable(onClick = {  viewModel.addToMyPlants(id) })
            ) {
                Image(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Favorite",
                    modifier = Modifier.size(18.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)

                )
                Image(
                    painter = painterResource(id = R.drawable.potted_plant),
                    contentDescription = "Share",
                    modifier = Modifier.size(18.dp)
                )
            }
        else
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.check_icon),
                    contentDescription = "Share",
                    modifier = Modifier.size(18.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreScreenPreview() {
    TrackPoopTheme {
        ExploreScreen(navController = rememberNavController())
    }
}
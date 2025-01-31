package com.ahmed.trackpoop.presentation.on_boarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

@Composable
fun OnBoardingScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    var currentPage by remember { mutableStateOf(0) }
    
    val pages = listOf(
        OnBoardingState(
            image = R.drawable.pooppng,
            title = "Never Miss a Poop\nAgain!",
            description = "We will send you reminders for everything\nrelated to your poop"
        ),
        OnBoardingState(
            image = R.drawable.poop1,
            title = "Locate your poop history",
            description = "Save every location where you’ve pooped on the map"
        ),
        OnBoardingState(
            image = R.drawable.poop3,
            title = "Learn more about your\nPoop health",
            description = "Learn more about poop health and\ncare guide."
        )
    )

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7B800))
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
            .padding(vertical = 70.dp)

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Main Image
            Image(
                painter = painterResource(id = pages[currentPage].image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .width(400.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(500.dp)) // Half of the height to make it circular

            )

            // Content Section
            Column(
                verticalArrangement = Arrangement.spacedBy(26.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = pages[currentPage].title,
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = pages[currentPage].description,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color(0xFF525252),
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        // Bottom Navigation Section
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Dots Indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    Image(
                        painter = painterResource(
                            id = if (currentPage == index) R.drawable.dot_active else R.drawable.dot
                        ),
                        contentDescription = "Page indicator",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }

            // Next Button
            Button(
                onClick = {
                    if (currentPage < 2) {
                        currentPage++
                    } else {
                        navController.navigate(Screen.WelcomeScreen.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF582900)
                ),
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    text = if (currentPage < 2) "Next" else "Get Started",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewWelcomeScreen() {
    TrackPoopTheme {
        val navController = rememberNavController() 
        OnBoardingScreen(navController)
    }

}

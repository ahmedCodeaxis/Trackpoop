package com.ahmed.trackpoop.presentation.welcome

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun WelcomeScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: WelcomeViewModel = koinViewModel()
) {
    val state = viewModel.state
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7B800))
    ) {
        Image(
            painter = painterResource(R.drawable.welcome),
            contentDescription = "Welcome Background",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 200.dp)
                .height(800.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .padding(vertical = 70.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 26.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.Start)
                    .wrapContentSize(),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize(),
                    text = "Welcome",
                    color = Color(0XFF582900),
                    fontSize = 58.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize(),
                    text = "We're glad that you \nare here!",
                    color = Color(0xff582900),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!state.isLoggedIn) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xff582900), RoundedCornerShape(100.dp))
                            .clip(RoundedCornerShape(100.dp))
                            .clickable { navController.navigate(Screen.SignInScreen.route) }
                            .padding(horizontal = 24.dp, vertical = 10.dp),
                    ) {
                        Text(
                            text = "Let's Get Started",
                            color = Color(0xffffffff),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                if (state.isLoggedIn && state.user != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xff129c52), RoundedCornerShape(100.dp))
                            .clip(RoundedCornerShape(100.dp))
                            .clickable { navController.navigate(Screen.MainScreen.route) }
                            .padding(horizontal = 24.dp, vertical = 10.dp),
                    ) {
                        Text(
                            text = "Continue as ${state.user.name}",
                            color = Color(0xffffffff),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWelcomeScreen() {
    TrackPoopTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}
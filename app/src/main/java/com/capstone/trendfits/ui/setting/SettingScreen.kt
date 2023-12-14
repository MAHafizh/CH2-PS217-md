package com.capstone.trendfits.ui.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.trendfits.R
import com.capstone.trendfits.ui.components.SettingItem
import com.capstone.trendfits.ui.theme.TrendFitsTheme

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        SettingContent()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = modifier
                        .height(58.dp)
                        .padding(bottom = 16.dp)
                )
                Box(
                    modifier = modifier
                        .size(68.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "profile",
                        modifier = modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = "User",
                    modifier = modifier.padding(top = 8.dp, bottom = 20.dp)
                )
                SettingItem(
                    title = "Edit Profile", modifier = modifier.padding(bottom = 16.dp)
                )
                SettingItem(
                    title = "Log Out"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    TrendFitsTheme {
        SettingScreen()
    }
}
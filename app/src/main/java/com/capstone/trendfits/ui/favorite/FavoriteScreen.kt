package com.capstone.trendfits.ui.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.capstone.trendfits.R
import com.capstone.trendfits.ui.theme.TrendFitsTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun FavoriteScreen(
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
@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
) {
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }

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
                        painter = rememberAsyncImagePainter(user!!.photoUrl),
                        contentDescription = "profile",
                        modifier = modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = user!!.displayName!!,
                    modifier = modifier.padding(top = 8.dp, bottom = 20.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    TrendFitsTheme {
        FavoriteScreen()
    }
}
package com.capstone.trendfits.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.capstone.trendfits.R
import com.capstone.trendfits.di.Injection
import com.capstone.trendfits.model.ClothesOrder
import com.capstone.trendfits.ui.ViewModelFactory
import com.capstone.trendfits.ui.components.ClothesItem
import com.capstone.trendfits.ui.components.HomeSection
import com.capstone.trendfits.ui.components.Search
import com.capstone.trendfits.ui.components.StateUi
import com.capstone.trendfits.ui.theme.TrendFitsTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {

    var selectedTabIndex by remember { mutableStateOf(0) }

    viewModel.stateUi.collectAsState(initial = StateUi.Loading).value.let { stateUi ->
        when (stateUi) {
            is StateUi.Loading -> {
                viewModel.getAllClothes()
            }

            is StateUi.Success -> {
                ClothesContent(
                    clothesOrder = stateUi.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    selectedTabIndex = selectedTabIndex
                ) { index ->
                    selectedTabIndex = index
                }
            }

            is StateUi.Error -> {}
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClothesContent(
    clothesOrder: List<ClothesOrder>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                    HomeSection(
                        title = "Hi, ${user!!.displayName}",
                    )
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(user!!.photoUrl),
                            contentDescription = "profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Text(
                text = "Discover your style",
            )
            Search()

            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { onTabSelected(0) },
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    Text(text = "Wardrobe")
                }
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { onTabSelected(1) },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    Text(text = "Outfit")
                }
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { onTabSelected(2) },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    Text(text = "Category")
                }
            }


            LazyVerticalGrid(
                columns = GridCells.Adaptive(140.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = modifier
            ) {
                items(clothesOrder) { data ->
                    ClothesItem(
                        image = data.clothes.image,
                        title = data.clothes.title,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.clothes.id)
                        }
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun HomePreview() {
    TrendFitsTheme {
        HomeScreen(navigateToDetail = { /* Define a dummy lambda function */ })
    }
}
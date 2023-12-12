package com.capstone.trendfits.ui.home

import Search
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.trendfits.R
import com.capstone.trendfits.di.Injection
import com.capstone.trendfits.model.ClothesOrder
import com.capstone.trendfits.ui.ViewModelFactory
import com.capstone.trendfits.ui.components.CategoryItem
import com.capstone.trendfits.ui.components.ClothesItem
import com.capstone.trendfits.ui.components.HomeSection
import com.capstone.trendfits.ui.components.StateUi
import com.capstone.trendfits.ui.theme.TrendFitsTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
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
                )
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
) {

    val image = painterResource(id = R.drawable.profile)

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HomeSection(
                        title = "Hi! Hafizh"
                    )
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = image,
                            contentDescription = "profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Text(
                    text = "Discover your style",
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                )
                Search()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryItem(
                        title = "Wardrobe",
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                    CategoryItem(
                        title = "Outfit",
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                    CategoryItem(
                        title = "Category",
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(140.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
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
    )
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    TrendFitsTheme {
        HomeScreen(navigateToDetail = { /* Define a dummy lambda function */ })
    }
}
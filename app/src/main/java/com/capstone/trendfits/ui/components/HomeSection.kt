package com.capstone.trendfits.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeSection(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        SectionText(title, modifier)
    }
}
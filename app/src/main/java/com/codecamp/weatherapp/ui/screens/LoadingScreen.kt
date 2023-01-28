package com.codecamp.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.weatherapp.R

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.Autorenew,
            contentDescription = stringResource(R.string.loading),
            modifier = Modifier.size(100.dp)
        )
        Spacer(
            modifier = Modifier.size(15.dp)
        )
        Text(
            text = stringResource(R.string.loading),
            color = MaterialTheme.colors.onSurface,
            fontSize = 20.sp
        )
    }
}
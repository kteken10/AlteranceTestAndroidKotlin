package com.example.beelditechtest.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.R
import com.example.beelditechtest.ui.theme.screenBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentListTopAppBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo_beeldi),
                contentDescription = "Logo Beeldi",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(22.dp),
            )
        },
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackground,
        ),
        modifier = modifier,
    )
}

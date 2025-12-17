package com.example.beelditechtest.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.R
import com.example.beelditechtest.ui.theme.screenBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentListTopAppBar(
    userAvatarResId: Int? = null,
    onAvatarClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo_beeldi),
                contentDescription = "Logo Beeldi",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(28.dp),
            )
        },
        title = { },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable(onClick = onAvatarClick),
                contentAlignment = Alignment.Center,
            ) {
                if (userAvatarResId != null) {
                    Image(
                        painter = painterResource(id = userAvatarResId),
                        contentDescription = "Avatar utilisateur",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_default_avatar),
                        contentDescription = "Avatar par défaut",
                        modifier = Modifier.size(28.dp),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackground,
        ),
        modifier = modifier,
    )
}

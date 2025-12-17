package com.example.beelditechtest.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
    isDarkTheme: Boolean = false,
    onNotificationClick: () -> Unit = {},
    onThemeToggle: () -> Unit = {},
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
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Icône Notification
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable(onClick = onNotificationClick),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(22.dp),
                        tint = Color(0xFF0F0F0F),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Icône Thème
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable(onClick = onThemeToggle),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_theme),
                        contentDescription = if (isDarkTheme) "Thème clair" else "Thème sombre",
                        modifier = Modifier.size(22.dp),
                        tint = Color(0xFF0F0F0F),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Avatar Utilisateur
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White)
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
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackground,
        ),
        modifier = modifier,
    )
}

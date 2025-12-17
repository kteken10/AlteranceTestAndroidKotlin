package com.example.beelditechtest.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beelditechtest.R
import com.example.beelditechtest.ui.theme.error
import com.example.beelditechtest.ui.theme.screenBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentListTopAppBar(
    modifier: Modifier = Modifier,
    notificationCount: Int = 0,
    isDarkTheme: Boolean = false,
    onAvatarClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onThemeToggleClick: () -> Unit = {},
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
        actions = {
            // Notification icon with badge
            Box(
                modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(onClick = onNotificationClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notifications),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF0F0F0F)
                    )
                }
                
                // Badge
                if (notificationCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .offset(x = 8.dp, y = 8.dp)
                            .background(error, CircleShape)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (notificationCount > 9) "9+" else notificationCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Theme toggle icon
            IconButton(
                onClick = onThemeToggleClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isDarkTheme) R.drawable.ic_dark_mode else R.drawable.ic_light_mode
                    ),
                    contentDescription = "Basculer le thème",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF0F0F0F)
                )
            }
            
            // User avatar
            Image(
                painter = painterResource(id = R.drawable.ic_user_avatar),
                contentDescription = "Avatar utilisateur",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onAvatarClick)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackground,
        ),
        modifier = modifier,
    )
}

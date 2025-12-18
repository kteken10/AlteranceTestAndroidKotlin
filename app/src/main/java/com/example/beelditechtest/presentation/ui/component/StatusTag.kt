package com.example.beelditechtest.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beelditechtest.domain.model.EquipmentStatus

@Composable
fun StatusTag(
    status: EquipmentStatus,
    modifier: Modifier = Modifier,
) {
    val (borderColor, statusText) = when (status) {
        EquipmentStatus.OK -> Color(0xFF4CAF50) to "Conforme"
        EquipmentStatus.TO_COMPLETE -> Color(0xFFFF9800) to "À compléter"
        EquipmentStatus.DEFECT -> Color(0xFFF44336) to "En défaut"
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = Color.White,
        border = BorderStroke(
            width = 0.5.dp,
            color = borderColor.copy(alpha = 0.7f),
        ),
    ) {
        Text(
            text = statusText,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            color = borderColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

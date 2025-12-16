package com.example.beelditechtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.beelditechtest.presentation.ui.screen.EquipmentDetailScreen
import com.example.beelditechtest.presentation.ui.screen.EquipmentListScreen
import com.example.beelditechtest.presentation.viewmodel.EquipmentDetailViewModel
import com.example.beelditechtest.presentation.viewmodel.EquipmentListViewModel

sealed class Screen(val route: String) {
    data object EquipmentList : Screen("equipment_list")
    data object EquipmentDetail : Screen("equipment_detail/{equipmentId}") {
        fun createRoute(equipmentId: String) = "equipment_detail/$equipmentId"
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.EquipmentList.route,
        modifier = modifier,
    ) {
        composable(Screen.EquipmentList.route) {
            val viewModel: EquipmentListViewModel = hiltViewModel()
            EquipmentListScreen(
                viewModel = viewModel,
                onEquipmentClick = { equipmentId ->
                    navController.navigate(Screen.EquipmentDetail.createRoute(equipmentId))
                },
            )
        }

        composable(
            route = Screen.EquipmentDetail.route,
            arguments = listOf(
                navArgument("equipmentId") { type = NavType.StringType },
            ),
        ) {
            val viewModel: EquipmentDetailViewModel = hiltViewModel()
            EquipmentDetailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}


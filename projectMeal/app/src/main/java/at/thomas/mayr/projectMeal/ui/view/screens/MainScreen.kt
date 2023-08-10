package at.thomas.mayr.projectMeal.ui.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.ui.state.RecipeUiState

@Composable
fun MainScreen(
    navController: NavController,
    uiState: RecipeUiState
) {
    when(uiState) {
        RecipeUiState.Loading -> {Text(text = "Loading")}
        is RecipeUiState.Success -> {
            RecipeFeed(navController = navController, allRecipes = uiState.recipes)
        }
    }
}
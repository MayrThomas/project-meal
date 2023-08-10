package at.thomas.mayr.projectMeal.ui.state

import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient

sealed interface RecipeUiState {
    object Loading: RecipeUiState
    data class Success(val recipes: List<RecipeWithIngredient>): RecipeUiState
}
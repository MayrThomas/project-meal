package at.thomas.mayr.projectMeal.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.ui.state.RecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val recipeDate: Flow<List<RecipeWithIngredient>> = mealRepository.allRecipes

    val uiState: StateFlow<RecipeUiState> = recipeDate.map {
        RecipeUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = RecipeUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    fun addNewRecipe(recipe: Recipe): Recipe {
        return mealRepository.insertRecipe(recipe)
    }

    fun addNewIngredient(ingredient: Ingredient) {
        mealRepository.insertIngredient(ingredient)
    }
}
package at.thomas.mayr.projectMeal.room

import androidx.lifecycle.LiveData
import at.thomas.mayr.projectMeal.room.dao.MealDAO
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealRepository(private val mealDAO: MealDAO) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val allRecipes: LiveData<List<RecipeWithIngredient>> = mealDAO.getAllRecipes()

    fun insertRecipe(recipe: Recipe) {
        coroutineScope.launch {
            mealDAO.insertRecipe(recipe)
        }
    }

    fun insertIngredient(ingredient: Ingredient) {
        coroutineScope.launch {
            mealDAO.insertIngredient(ingredient)
        }
    }
}
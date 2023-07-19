package at.thomas.mayr.projectMeal.room

import androidx.lifecycle.LiveData
import at.thomas.mayr.projectMeal.room.dao.MealDAO
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MealRepository(private val mealDAO: MealDAO) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val allRecipes: LiveData<List<RecipeWithIngredient>> = mealDAO.getAllRecipes()

    fun insertRecipe(recipe: Recipe): Recipe {
        coroutineScope.launch {
            mealDAO.insertRecipe(recipe)
        }

        return getLastInsertedRecipe()
    }

    fun insertIngredient(ingredient: Ingredient) {
        coroutineScope.launch {
            mealDAO.insertIngredient(ingredient)
        }
    }

    private fun getLastInsertedRecipe(): Recipe = runBlocking {
        var lastRecipe = Recipe()

        val async = GlobalScope.async {
            lastRecipe = mealDAO.getLastInsertedRecipe()
        }

        async.await()

        return@runBlocking lastRecipe
    }
}
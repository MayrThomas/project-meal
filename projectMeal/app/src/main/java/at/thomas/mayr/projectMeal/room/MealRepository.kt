package at.thomas.mayr.projectMeal.room

import at.thomas.mayr.projectMeal.room.dao.MealDAO
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealDAO: MealDAO) {

    val allRecipes: Flow<List<RecipeWithIngredient>> = mealDAO.getAllRecipes()

    fun insertRecipe(recipe: Recipe): Recipe {
        mealDAO.insertRecipe(recipe)

        return getLastInsertedRecipe()
    }

    fun insertIngredient(ingredient: Ingredient) {
        mealDAO.insertIngredient(ingredient)
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
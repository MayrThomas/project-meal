package at.thomas.mayr.projectMeal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient

@Dao
interface MealDAO {

    @Insert
    fun insertRecipe(recipe: Recipe)

    @Insert
    fun insertIngredient(ingredient: Ingredient)

    @Transaction
    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): LiveData<List<RecipeWithIngredient>>

    @Transaction
    @Query("SELECT * FROM Recipe ORDER BY recipeId DESC LIMIT 1")
    fun getLastInsertedRecipe(): Recipe
}
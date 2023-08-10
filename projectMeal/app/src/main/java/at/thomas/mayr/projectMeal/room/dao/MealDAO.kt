package at.thomas.mayr.projectMeal.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDAO {

    @Insert
    fun insertRecipe(recipe: Recipe)

    @Insert
    fun insertIngredient(ingredient: Ingredient)

    @Transaction
    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): Flow<List<RecipeWithIngredient>>

    @Transaction
    @Query("SELECT * FROM Recipe ORDER BY recipeId DESC LIMIT 1")
    fun getLastInsertedRecipe(): Recipe
}
package at.thomas.mayr.projectMeal

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.thomas.mayr.projectMeal.room.MealDatabase
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.IngredientUnit
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.ui.theme.ProjectMealTheme
import at.thomas.mayr.projectMeal.util.Utils
import at.thomas.mayr.projectMeal.ui.theme.view.screens.MainScreen
import at.thomas.mayr.projectMeal.ui.theme.view.screens.RecipeScreen

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Database
        val mealDatabase = MealDatabase.getInstance(this.applicationContext)
        val mealDao = mealDatabase.mealDao()
        repository = MealRepository(mealDao)

        setContent {
            ProjectMealTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("home") {
                        MainScreen(
                            navController,
                            allRecipes = repository.allRecipes,
                            resources = resources
                        )
                    }
                    composable(
                        "recipe/{recipeId}"
                    ) { navBackStackEntry ->
                        val recipeId = navBackStackEntry.arguments?.getString("recipeId")?.toLong()

                        RecipeScreen(
                            navController = navController,
                            recipe = repository.allRecipes.value
                                ?.find { it.recipe.recipeId == recipeId }
                                ?: RecipeWithIngredient(Recipe(), listOf())
                        )
                    }
                }
            }
        }
    }

    companion object {
        lateinit var repository: MealRepository
        fun createTestRecipe(resources: Resources) {

            val testRecipe =
                Recipe(name = "TEST-RECIPE-NAME", image = Utils.bitmapToBase64(resources))
            val lastRecipe = repository.insertRecipe(testRecipe)

            val testIngredient = Ingredient(
                name = "Tomate",
                recipeCreatorId = lastRecipe.recipeId,
                ingredientUnit = IngredientUnit.G,
                amount = 150f
            )

            repository.insertIngredient(testIngredient)
            repository.insertIngredient(testIngredient)

        }
    }
}
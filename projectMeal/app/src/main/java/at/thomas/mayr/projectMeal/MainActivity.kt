package at.thomas.mayr.projectMeal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import at.thomas.mayr.projectMeal.room.MealDatabase
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.IngredientUnit
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.ui.theme.ProjectMealTheme
import at.thomas.mayr.projectMeal.view.EmptyRecipeGridItem
import at.thomas.mayr.projectMeal.view.MealFAB
import at.thomas.mayr.projectMeal.view.RecipeGridItem

class MainActivity : ComponentActivity() {
    lateinit var repository: MealRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectMealTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = { TopAppBar(
                        title = { Text(text = "Recipes")},
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            scrolledContainerColor = MaterialTheme.colorScheme.primary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )},
                    floatingActionButton = {
                        MealFAB(
                            icon = Icons.Default.Add,
                            contentDescription = "Add test recipe",
                            onClick = {
                                val testRecipe = Recipe(name = "TEST-RECIPE-NAME")
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
                        )
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) {
                    ScreenSetup(it)
                }
            }
        }
    }

    @Composable
    fun ScreenSetup(paddingValues: PaddingValues) {
        // Setup Database
        val mealDatabase = MealDatabase.getInstance(this.applicationContext)
        val mealDao = mealDatabase.mealDao()
        repository = MealRepository(mealDao)

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
            MainScreen(repository.allRecipes)
        }
    }

    @Composable
    fun MainScreen(allRecipes: LiveData<List<RecipeWithIngredient>>) {
        val recipes by allRecipes.observeAsState(initial = emptyList())

        if(recipes.isEmpty()) {
            EmptyRecipeGridItem()
        } else {
            LazyVerticalGrid(
                modifier = Modifier.padding(8.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recipes) { recipeWithIngredients ->
                    RecipeGridItem(
                        recipeWithIngredients = recipeWithIngredients,
                        onClick = { Toast.makeText(applicationContext, "Click on recipe with id ${recipeWithIngredients.recipe.recipeId}", Toast.LENGTH_SHORT).show()}
                    )
                }
            }
        }
    }
}
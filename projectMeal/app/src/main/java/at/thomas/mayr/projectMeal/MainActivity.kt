package at.thomas.mayr.projectMeal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import at.thomas.mayr.projectMeal.room.MealDatabase
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.IngredientUnit
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.ui.theme.ProjectMealTheme

class MainActivity : ComponentActivity() {
    lateinit var repository: MealRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectMealTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ScreenSetup()
                }
            }
        }
    }

    @Composable
    fun ScreenSetup() {
        // Setup Database
        val mealDatabase = MealDatabase.getInstance(this.applicationContext)
        val mealDao = mealDatabase.mealDao()
        repository = MealRepository(mealDao)

        Column(Modifier.fillMaxSize()) {
            MainScreen(repository.allRecipes)
        }
    }

    @Composable
    fun MainScreen(allRecipes: LiveData<List<RecipeWithIngredient>>) {
        val recipes by allRecipes.observeAsState(initial = emptyList())

        if(recipes.isEmpty()) {
            Card(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Recipes are empty")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(recipes) {recipeWithIngredients ->
                    Card(
                        modifier = Modifier.background(Color.LightGray).padding(8.dp),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(text = recipeWithIngredients.recipe.name)
                        for (ingredient in recipeWithIngredients.ingredients) {
                            Text(text = "${ingredient.amount} ${ingredient.ingredientUnit?.name} ${ingredient.name}")
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                onClick = {
                    //OnClick Method
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

                },
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add FAB",
                    tint = Color.White,
                )
            }
        }
    }
}
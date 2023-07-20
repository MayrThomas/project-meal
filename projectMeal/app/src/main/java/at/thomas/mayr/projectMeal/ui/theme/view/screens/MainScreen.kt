package at.thomas.mayr.projectMeal.ui.theme.view.screens

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
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
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.MainActivity
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.ui.theme.view.views.EmptyRecipeGridItem
import at.thomas.mayr.projectMeal.ui.theme.view.views.MealFAB
import at.thomas.mayr.projectMeal.ui.theme.view.views.RecipeGridItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    allRecipes: LiveData<List<RecipeWithIngredient>>,
    resources: Resources
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Recipes") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            MealFAB(
                icon = Icons.Default.Add,
                contentDescription = "Add test recipe",
                onClick = { MainActivity.createTestRecipe(resources) }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        val recipes by allRecipes.observeAsState(initial = emptyList())

        if (recipes.isEmpty()) {
            EmptyRecipeGridItem(it)
        } else {
            LazyVerticalGrid(
                modifier = Modifier.padding(it),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recipes) { recipeWithIngredients ->
                    RecipeGridItem(
                        recipeWithIngredients = recipeWithIngredients,
                        onClick = {
                            val id = recipeWithIngredients.recipe.recipeId
                            navController.navigate("recipe/$id")
                        }
                    )
                }
            }
        }
    }
}
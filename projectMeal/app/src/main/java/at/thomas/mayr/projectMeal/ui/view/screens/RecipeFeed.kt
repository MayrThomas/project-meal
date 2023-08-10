package at.thomas.mayr.projectMeal.ui.view.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.ui.view.views.EmptyRecipeGridItem
import at.thomas.mayr.projectMeal.ui.view.views.MealFAB
import at.thomas.mayr.projectMeal.ui.view.views.RecipeGridItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeFeed(
    navController: NavController,
    allRecipes: List<RecipeWithIngredient>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Recipes") },
                colors = TopAppBarDefaults.topAppBarColors(
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
                onClick = { navController.navigate("add") }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        if (allRecipes.isEmpty()) {
            EmptyRecipeGridItem(it)
        } else {
            LazyVerticalGrid(
                modifier = Modifier.padding(it),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allRecipes) { recipeWithIngredients ->
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
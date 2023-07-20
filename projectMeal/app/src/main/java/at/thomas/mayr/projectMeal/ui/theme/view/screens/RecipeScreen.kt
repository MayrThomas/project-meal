package at.thomas.mayr.projectMeal.ui.theme.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.core.ImageConversionUtils
import at.thomas.mayr.projectMeal.ui.theme.view.views.MealFAB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(navController: NavController, recipe: RecipeWithIngredient) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = recipe.recipe.name) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        enabled = true
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MealFAB(
                onClick = { /*TODO*/ },
                icon = Icons.Default.ShoppingCart,
                contentDescription = "Create shopping list from recipe"
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                bitmap = ImageConversionUtils.base64ToBitmap(recipe.recipe.image),
                contentDescription = "Image of Meal"
            )

            Text(
                text = "Ingredients",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(recipe.ingredients) {ingredient ->
                    Text(text = "${ingredient.amount} ${ingredient.ingredientUnit} ${ingredient.name}")
                }
            }

            Text(
                text = "Steps",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                // TODO: include steps to recreate recipe
            }
        }
    }
}
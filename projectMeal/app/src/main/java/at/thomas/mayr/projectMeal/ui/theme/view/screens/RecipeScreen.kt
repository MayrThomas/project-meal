package at.thomas.mayr.projectMeal.ui.theme.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.util.Utils

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
    ) {
        Image(
            bitmap = Utils.base64ToBitmap(recipe.recipe.image),
            contentDescription = "Image of Meal",
            modifier = Modifier.padding(it)
        )
    }
}
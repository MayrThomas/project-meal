package at.thomas.mayr.projectMeal.ui.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.ui.view.views.CreateIngredientDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(navController: NavController, repository: MealRepository) {
    val recipeName = remember { mutableStateOf(TextFieldValue()) }
    val ingredients = remember { mutableStateListOf<Ingredient>() }
    val steps = remember { mutableStateListOf(String()) }
    val showCreateIngredientDialog = remember { mutableStateOf(false) }

    if (showCreateIngredientDialog.value) {
        CreateIngredientDialog (
            setShowDialog = { showCreateIngredientDialog.value = it },
            addIngredient = { ingredients.add(it) }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Recipe") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            Text(text = "Cool New Recipe")

            TextField(
                value = recipeName.value,
                onValueChange = { name -> recipeName.value = name }
            )

            LazyColumn {
                items(ingredients.toList()) {ingredient ->
                    Text(text = "${ingredient.amount} ${ingredient.ingredientUnit?.name} ${ingredient.name}")
                }
            }
            IconButton(onClick = {
                showCreateIngredientDialog.value = true
            }) {
                Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "Add Ingredient Button")
            }

            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = IconButtonDefaults.filledIconButtonColors()
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add recipe button")
            }
        }
    }
}
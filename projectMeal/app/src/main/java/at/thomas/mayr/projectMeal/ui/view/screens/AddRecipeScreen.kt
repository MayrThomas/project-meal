package at.thomas.mayr.projectMeal.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.R
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.ui.view.views.CreateIngredientDialog
import at.thomas.mayr.projectMeal.ui.view.views.CreateStepDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(navController: NavController, repository: MealRepository) {
    val recipeName = remember { mutableStateOf(TextFieldValue()) }
    val ingredients = remember { mutableStateListOf<Ingredient>() }
    val steps = remember { mutableStateListOf<String>() }
    val showCreateIngredientDialog = remember { mutableStateOf(false) }
    val showCreateStepDialog = remember { mutableStateOf(false) }

    if (showCreateIngredientDialog.value) {
        CreateIngredientDialog (
            setShowDialog = { showCreateIngredientDialog.value = it },
            addIngredient = { ingredients.add(it) }
        )
    }

    if(showCreateStepDialog.value) {
        CreateStepDialog(
            setShowDialog = { showCreateStepDialog.value = it },
            addStep = { steps.add(it)}
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
        Box (modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.chef_hat),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        // TODO take image using camera or use image from disk
                    }
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = recipeName.value,
                    onValueChange = { name -> recipeName.value = name },
                    label = { Text(text = "Recipe Name") }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Ingredients", fontWeight = FontWeight.Bold)

                    IconButton(onClick = {
                        showCreateIngredientDialog.value = true
                    }) {
                        Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "Add Ingredient Button")
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ingredients) {ingredient ->
                        Text(text = "${ingredient.amount} ${ingredient.ingredientUnit?.name} ${ingredient.name}")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Steps", fontWeight = FontWeight.Bold)

                    IconButton(onClick = {
                        showCreateStepDialog.value = true
                    }) {
                        Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "Add Ingredient Button")
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(steps) {index: Int, step: String ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "$index:", fontWeight = FontWeight.SemiBold)
                            Text(text = step)
                        }
                    }
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
}
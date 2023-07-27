package at.thomas.mayr.projectMeal.ui.view.screens

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.thomas.mayr.projectMeal.MainActivity
import at.thomas.mayr.projectMeal.R
import at.thomas.mayr.projectMeal.core.ImageConversionUtils
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.ui.view.views.CameraView
import at.thomas.mayr.projectMeal.ui.view.views.CreateIngredientDialog
import at.thomas.mayr.projectMeal.ui.view.views.CreateStepDialog
import at.thomas.mayr.projectMeal.ui.view.views.MealFAB
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    repository: MealRepository,
    activity: MainActivity
) {
    val chefHat = ImageBitmap.imageResource(id = R.drawable.chef_hat)
    val recipeImage = remember { mutableStateOf(chefHat) }
    val recipeName = remember { mutableStateOf(TextFieldValue()) }
    val ingredients = remember { mutableStateListOf<Ingredient>() }
    val steps = remember { mutableStateListOf<String>() }
    val showCreateIngredientDialog = remember { mutableStateOf(false) }
    val showCreateStepDialog = remember { mutableStateOf(false) }
    val showCameraView = remember { mutableStateOf(false) }

    if (showCreateIngredientDialog.value) {
        CreateIngredientDialog(
            setShowDialog = { showCreateIngredientDialog.value = it },
            addIngredient = { ingredients.add(it) }
        )
    }

    if (showCreateStepDialog.value) {
        CreateStepDialog(
            setShowDialog = { showCreateStepDialog.value = it },
            addStep = { steps.add(it) }
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
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close button"
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            MealFAB(
                onClick = {
                    val recipe = Recipe(
                        name = recipeName.value.text,
                        image = ImageConversionUtils.bitmapToBase64(recipeImage.value.asAndroidBitmap()),
                        steps = steps.toList()
                    )

                    val inserted = repository.insertRecipe(recipe)

                    ingredients.forEach {
                        it.recipeCreatorId = inserted.recipeId

                        repository.insertIngredient(it)
                    }

                    navController.navigateUp()
                },
                icon = Icons.Default.Done,
                contentDescription = "Save button"
            )
        }
    ) { it ->
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (showCameraView.value) {
                    CameraView(
                        executor = Executors.newSingleThreadExecutor(),
                        onImageCaptured = { proxy ->
                            val matrix = Matrix()
                            matrix.postRotate(90f)

                            val bitmap = proxy.toBitmap()
                            recipeImage.value = Bitmap.createBitmap(
                                bitmap,
                                0,
                                0,
                                bitmap.width,
                                bitmap.height,
                                matrix,
                                true
                            ).asImageBitmap()
                            showCameraView.value = false
                        },
                        onError = {
                            showCameraView.value = false
                        }
                    )
                }

                Image(
                    bitmap = recipeImage.value,
                    contentDescription = "Image of food",
                    modifier = Modifier.clickable {
                        activity.requestCameraPermission(showCameraView)
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
                        Icon(
                            imageVector = Icons.Outlined.AddCircle,
                            contentDescription = "Add Ingredient Button"
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ingredients.forEach { ingredient ->
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
                        Icon(
                            imageVector = Icons.Outlined.AddCircle,
                            contentDescription = "Add Ingredient Button"
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    steps.forEachIndexed { index, step ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "${index + 1}:", fontWeight = FontWeight.SemiBold)
                            Text(text = step)
                        }
                    }
                }
            }
        }
    }
}
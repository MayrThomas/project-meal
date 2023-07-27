package at.thomas.mayr.projectMeal

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.thomas.mayr.projectMeal.room.MealDatabase
import at.thomas.mayr.projectMeal.room.MealRepository
import at.thomas.mayr.projectMeal.room.entities.Recipe
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.ui.theme.ProjectMealTheme
import at.thomas.mayr.projectMeal.ui.view.screens.AddRecipeScreen
import at.thomas.mayr.projectMeal.ui.view.screens.MainScreen
import at.thomas.mayr.projectMeal.ui.view.screens.RecipeScreen

class MainActivity : ComponentActivity() {

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
                    composable("add") {
                        AddRecipeScreen(
                            navController = navController,
                            repository = repository,
                            activity = this@MainActivity
                        )
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            Log.i("ProjectMeal", "isGranted")
        } else {
            Log.i("ProjectMeal", "notGranted")
        }
    }

    fun requestCameraPermission(
        showCamera: MutableState<Boolean>
    ) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("ProjectMeal", "Permission already granted")
                showCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> Log.i("ProjectMeal", "Show camera permission dialog")

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    companion object {
        lateinit var repository: MealRepository
    }
}
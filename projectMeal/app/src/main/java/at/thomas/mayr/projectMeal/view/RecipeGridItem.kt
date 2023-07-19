package at.thomas.mayr.projectMeal.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient

@Composable
fun RecipeGridItem(recipeWithIngredients: RecipeWithIngredient) {
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(imageVector = Icons.Filled.Menu,
                contentDescription = "image of the food from the recipe"
            )
            Text(text = recipeWithIngredients.recipe.name,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible
            )
        }
    }
}
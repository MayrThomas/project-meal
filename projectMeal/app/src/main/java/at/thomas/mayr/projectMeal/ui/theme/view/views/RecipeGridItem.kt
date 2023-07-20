package at.thomas.mayr.projectMeal.ui.theme.view.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import at.thomas.mayr.projectMeal.room.entities.RecipeWithIngredient
import at.thomas.mayr.projectMeal.util.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeGridItem(recipeWithIngredients: RecipeWithIngredient, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                Utils.base64ToBitmap(recipeWithIngredients.recipe.image),
                contentDescription = "image of the food from the recipe",
            )
            Text(
                text = recipeWithIngredients.recipe.name,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.Center
            )
        }
    }
}
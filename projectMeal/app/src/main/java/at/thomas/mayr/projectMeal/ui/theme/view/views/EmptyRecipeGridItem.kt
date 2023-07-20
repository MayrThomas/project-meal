package at.thomas.mayr.projectMeal.ui.theme.view.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.thomas.mayr.projectMeal.R

@Composable
fun EmptyRecipeGridItem(paddingValues: PaddingValues) {
    Box(
        Modifier.fillMaxSize().padding(paddingValues)
    ) {
        Column(
            Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.chef_hat),
                contentDescription = "Empty recipe list icon",
            )
            Text(
                text = "No recipes available!!",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
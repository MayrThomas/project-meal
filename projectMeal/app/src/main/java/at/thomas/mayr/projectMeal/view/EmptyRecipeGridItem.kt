package at.thomas.mayr.projectMeal.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import at.thomas.mayr.projectMeal.R

@Composable
fun EmptyRecipeGridItem() {
    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.chef_hat),
            contentDescription = "Empty recipe list icon",
            Modifier.align(Alignment.Center)
        )
    }
}
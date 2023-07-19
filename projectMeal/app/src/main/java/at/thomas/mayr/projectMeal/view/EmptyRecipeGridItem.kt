package at.thomas.mayr.projectMeal.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun EmptyRecipeGridItem() {
    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            imageVector = Icons.Default.Info,
            contentDescription = "Empty recipe list icon",
            Modifier.align(Alignment.Center)
        )
    }
}
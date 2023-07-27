package at.thomas.mayr.projectMeal.ui.view.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import at.thomas.mayr.projectMeal.room.entities.Ingredient
import at.thomas.mayr.projectMeal.room.entities.IngredientUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIngredientDialog(setShowDialog: (Boolean) -> Unit, addIngredient: (Ingredient) -> Unit) {
    val unitList = IngredientUnit.values()
    val ingredientAmount = remember { mutableStateOf(0.0F) }
    val ingredientName = remember { mutableStateOf("") }
    val ingredientUnit = remember { mutableStateOf(unitList[0]) }
    val expanded = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Add Ingredient", fontWeight = FontWeight.Bold)

                    TextField(
                        value = ingredientName.value,
                        onValueChange = { name -> ingredientName.value = name },
                        label = { Text(text = "Name") }
                    )

                    TextField(
                        value = ingredientAmount.value.toString(),
                        onValueChange = { value -> ingredientAmount.value = value.toFloat() },
                        label = { Text(text = "Amount") }
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value }
                    ) {
                        TextField(
                            readOnly = true,
                            value = ingredientUnit.value.name,
                            onValueChange = { },
                            label = { Text("Label") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded.value
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            unitList.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.name) },
                                    onClick = {
                                        ingredientUnit.value = it
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Button(
                            onClick = {
                                val ingredient = Ingredient(
                                    name = ingredientName.value,
                                    amount = ingredientAmount.value,
                                    ingredientUnit = ingredientUnit.value
                                )
                                addIngredient(ingredient)
                                setShowDialog(false)
                            },
                        ) {
                            Text(text = "Done")
                        }
                    }
                }
            }
        }
    }
}
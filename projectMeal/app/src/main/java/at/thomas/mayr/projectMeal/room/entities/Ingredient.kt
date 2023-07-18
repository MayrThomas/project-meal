package at.thomas.mayr.projectMeal.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity
data class Ingredient (
    @PrimaryKey(autoGenerate = true)
    var ingredientId: Long = 0,

    var name: String = "",

    var recipeCreatorId: Long = 0,

    @ColumnInfo(name = "ingredientUnit")
    @TypeConverters(IngredientUnitConverter::class)
    var ingredientUnit: IngredientUnit? = null,

    var amount: Float = 0f
)
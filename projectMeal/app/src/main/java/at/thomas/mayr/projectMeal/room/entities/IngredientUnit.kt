package at.thomas.mayr.projectMeal.room.entities

import androidx.room.TypeConverter

enum class IngredientUnit {
    KG, G, TBS, TS, CUP
}

object IngredientUnitConverter {

    @TypeConverter
    fun toIngredientUnit(value: String) = enumValueOf<IngredientUnit>(value)

    @TypeConverter
    fun fromIngredientUnit(value: IngredientUnit) = value.name
}
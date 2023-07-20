package at.thomas.mayr.projectMeal.room.entities

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object IngredientUnitConverter {

    @TypeConverter
    fun toIngredientUnit(value: String) = enumValueOf<IngredientUnit>(value)

    @TypeConverter
    fun fromIngredientUnit(value: IngredientUnit) = value.name
}

object StepListConverter {

    val gson = GsonBuilder().create()

    @TypeConverter
    fun toStepList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStepList(value: List<String>): String {
        return gson.toJson(value)
    }
}
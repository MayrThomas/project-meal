package at.thomas.mayr.projectMeal.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe (

    @PrimaryKey(autoGenerate = true)
    var recipeId: Long = 0,
    var name: String = "",
    var image: String = "",
    var steps: List<String> = listOf()
)
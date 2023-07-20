package at.thomas.mayr.projectMeal.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredient (
    @Embedded var recipe: Recipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeCreatorId"
    )
    var ingredients: List<Ingredient>

    /*
        TODO:   create list for steps of recipe
                create steps entity
                with stepDescription, stepNumber, recipeCreatorId
     */
)

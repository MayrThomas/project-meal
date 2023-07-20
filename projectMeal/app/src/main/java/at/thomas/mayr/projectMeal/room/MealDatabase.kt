package at.thomas.mayr.projectMeal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.thomas.mayr.projectMeal.room.dao.MealDAO
import at.thomas.mayr.projectMeal.room.entities.*

@Database(
    version = 2,
    entities = [Recipe::class, Ingredient::class]
)
@TypeConverters(IngredientUnitConverter::class, StepListConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDAO

    companion object {
        private var INSTANCE: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MealDatabase::class.java,
                        "meal_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
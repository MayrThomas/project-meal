package at.thomas.mayr.projectMeal.room

import android.content.Context
import androidx.room.Room
import at.thomas.mayr.projectMeal.room.dao.MealDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MealDatabaseModule {

    @Provides
    fun provideMealDao(mealDatabase: MealDatabase): MealDAO {
        return mealDatabase.mealDao()
    }

    @Singleton
    @Provides
    fun provideMealDatabase(@ApplicationContext context: Context): MealDatabase {
        return Room.databaseBuilder(
            context,
            MealDatabase::class.java,
            "meal_database"
        ).build()
    }
}
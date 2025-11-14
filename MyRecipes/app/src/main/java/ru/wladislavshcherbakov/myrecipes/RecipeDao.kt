package ru.wladislavshcherbakov.myrecipes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}
package ru.wladislavshcherbakov.myrecipes

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insertRecipe(recipe: Recipe) = recipeDao.insertRecipe(recipe)

    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)
}
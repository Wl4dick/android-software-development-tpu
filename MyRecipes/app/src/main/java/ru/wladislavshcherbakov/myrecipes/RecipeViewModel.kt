package ru.wladislavshcherbakov.myrecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    val allRecipes: Flow<List<Recipe>> = repository.getAllRecipes()

    fun insertRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.insertRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.deleteRecipe(recipe)
    }
}
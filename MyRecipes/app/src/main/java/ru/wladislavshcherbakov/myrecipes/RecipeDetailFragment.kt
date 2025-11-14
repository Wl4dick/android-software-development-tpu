package ru.wladislavshcherbakov.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
    private var recipeId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getLong("recipeId", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        loadRecipeDetails()
    }

    private fun setupViewModel() {
        val database = AppDatabase.getInstance(requireContext())
        val repository = RecipeRepository(database.recipeDao())
        val factory = ViewModelFactory(repository)

        viewModel = androidx.lifecycle.ViewModelProvider(this, factory)[RecipeViewModel::class.java]
    }

    private fun loadRecipeDetails() {
        if (recipeId == -1L) return

        lifecycleScope.launch {
            viewModel.allRecipes.collect { recipes ->
                val recipe = recipes.find { it.id == recipeId }
                recipe?.let { displayRecipeDetails(it) }
            }
        }
    }

    private fun displayRecipeDetails(recipe: Recipe) {
        view?.apply {
            findViewById<android.widget.TextView>(R.id.tv_detail_title).text = recipe.title
            findViewById<android.widget.TextView>(R.id.tv_detail_category).text = recipe.category
            findViewById<android.widget.TextView>(R.id.tv_detail_instructions).text = recipe.instructions
        }
    }

    companion object {
        fun newInstance(recipeId: Long): RecipeDetailFragment {
            val fragment = RecipeDetailFragment()
            val args = Bundle()
            args.putLong("recipeId", recipeId)
            fragment.arguments = args
            return fragment
        }
    }
}
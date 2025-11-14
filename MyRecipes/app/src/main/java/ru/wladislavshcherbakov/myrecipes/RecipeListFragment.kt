package ru.wladislavshcherbakov.myrecipes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var viewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupViewModel()
        setupSwipeToDelete()
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = RecipeAdapter { recipe ->
            // При клике на рецепт переходим к деталям
            navigateToRecipeDetails(recipe.id)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToRecipeDetails(recipeId: Long) {
        // Создаем фрагмент деталей и передаем ID рецепта
        val detailFragment = RecipeDetailFragment.newInstance(recipeId)

        // Используем Navigation Component для перехода
        findNavController().navigate(
            R.id.action_recipeList_to_recipeDetail,
            Bundle().apply {
                putLong("recipeId", recipeId)
            }
        )
    }

    private fun setupViewModel() {
        val database = AppDatabase.getInstance(requireContext())
        val repository = RecipeRepository(database.recipeDao())
        val factory = ViewModelFactory(repository)

        viewModel = androidx.lifecycle.ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        lifecycleScope.launch {
            viewModel.allRecipes.collect { recipes ->
                adapter.submitList(recipes)
            }
        }
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val recipe = adapter.currentList[position]

                showDeleteConfirmationDialog(recipe)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun showDeleteConfirmationDialog(recipe: Recipe) {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_recipe))
            .setMessage(getString(R.string.delete_recipe_confirmation))
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                deleteRecipe(recipe)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                adapter.notifyDataSetChanged()
            }
            .create()

        dialog.show()
    }

    private fun deleteRecipe(recipe: Recipe) {
        viewModel.deleteRecipe(recipe)

        Snackbar.make(
            requireView(),
            getString(R.string.recipe_deleted),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.undo)) {
            viewModel.insertRecipe(recipe)
        }.show()
    }
}
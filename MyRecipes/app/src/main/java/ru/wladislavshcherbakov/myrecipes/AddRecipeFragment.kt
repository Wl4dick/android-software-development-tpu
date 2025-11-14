package ru.wladislavshcherbakov.myrecipes

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddRecipeFragment : Fragment() {

    private lateinit var titleEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var instructionsEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var viewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)
        setupViewModel()
        setupSaveButton()
    }

    private fun setupViews(view: View) {
        titleEditText = view.findViewById(R.id.et_title)
        categorySpinner = view.findViewById(R.id.spinner_category)
        instructionsEditText = view.findViewById(R.id.et_instructions)
        saveButton = view.findViewById(R.id.btn_save)

        // Настройка спиннера категорий
        val categories = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    private fun setupViewModel() {
        val database = AppDatabase.getInstance(requireContext())
        val repository = RecipeRepository(database.recipeDao())
        val factory = ViewModelFactory(repository)

        viewModel = androidx.lifecycle.ViewModelProvider(this, factory)[RecipeViewModel::class.java]
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            if (validateForm()) {
                saveRecipe()
            }
        }
    }

    private fun validateForm(): Boolean {
        if (titleEditText.text.toString().trim().isEmpty()) {
            titleEditText.error = getString(R.string.recipe_name)
            return false
        }

        if (instructionsEditText.text.toString().trim().isEmpty()) {
            instructionsEditText.error = getString(R.string.instructions)
            return false
        }

        return true
    }

    private fun saveRecipe() {
        val title = titleEditText.text.toString().trim()
        val category = categorySpinner.selectedItem.toString()
        val instructions = instructionsEditText.text.toString().trim()

        val recipe = Recipe(
            title = title,
            category = category,
            instructions = instructions
        )

        viewModel.insertRecipe(recipe)

        Toast.makeText(
            requireContext(),
            getString(R.string.recipe_saved),
            Toast.LENGTH_SHORT
        ).show()

        findNavController().navigateUp()
    }
}
package ru.wladislavshcherbakov.myrecipes

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Предотвращаем сдвиг контента при открытии клавиатуры
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        // Устанавливаем Toolbar как ActionBar
        setSupportActionBar(findViewById(R.id.toolbar))

        // Устанавливаем заголовок
        supportActionBar?.title = getString(R.string.app_name)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_recipes -> {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.title = getString(R.string.recipes_list)
                }
                R.id.navigation_add_recipe -> {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.title = getString(R.string.add_recipe)
                }
                R.id.recipeDetailFragment -> {
                    navView.visibility = View.GONE
                    supportActionBar?.title = getString(R.string.recipe_details)
                }
                else -> {
                    navView.visibility = View.GONE
                }
            }
        }

        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
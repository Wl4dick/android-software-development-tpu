package ru.wladislavshcherbakov.myrecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private val onItemClicked: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)

        // Добавляем обработчик клика на весь элемент
        holder.itemView.setOnClickListener {
            onItemClicked(recipe)
        }
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val categoryTextView: TextView = itemView.findViewById(R.id.tv_category)

        fun bind(recipe: Recipe) {
            titleTextView.text = recipe.title
            categoryTextView.text = recipe.category
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}
package ru.wladislavshcherbakov.practice_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelsAdapter(
    private val levels: List<Level>,
    private val onClick: (Level) -> Unit
) : RecyclerView.Adapter<LevelsAdapter.LevelViewHolder>() {

    class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.levelName)
        val difficulty: TextView = itemView.findViewById(R.id.levelDifficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_level, parent, false)
        return LevelViewHolder(view)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val level = levels[position]
        holder.name.text = level.name
        holder.difficulty.text = level.difficulty
        holder.itemView.setOnClickListener { onClick(level) }
    }

    override fun getItemCount(): Int = levels.size
}
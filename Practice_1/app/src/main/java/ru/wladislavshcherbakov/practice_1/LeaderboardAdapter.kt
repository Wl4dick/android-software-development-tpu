package ru.wladislavshcherbakov.practice_1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.VH>() {

    private val items = mutableListOf<ScoreRecord>()

    fun submitList(list: List<ScoreRecord>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class VH(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val tv = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return VH(tv)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        // Используем функцию-расширение для форматирования даты
        holder.textView.text =
            "${item.playerName} — ${item.score} очков — ${item.date.formatDate()}"
    }

    override fun getItemCount(): Int = items.size
}

package ru.wladislavshcherbakov.lab15

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter(
    private var items: MutableList<ShoppingItem>,
    private val onItemClick: (ShoppingItem, Int) -> Unit
) : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvQuantity.text = holder.itemView.context.getString(R.string.quantity_format, item.quantity)

        holder.itemView.setOnClickListener {
            onItemClick(item, position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItem(position: Int, newItem: ShoppingItem) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    fun addItem(newItem: ShoppingItem) {
        items.add(newItem)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
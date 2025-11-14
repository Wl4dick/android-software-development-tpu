package ru.wladislavshcherbakov.lab23

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepositoryAdapter(
    private val onRepoClick: (String) -> Unit,
    private val onOwnerClick: (String) -> Unit
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var items = listOf<RepositoryItem>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.repoName)
        val ownerLogin: TextView = itemView.findViewById(R.id.ownerLogin)
        val repoDescription: TextView = itemView.findViewById(R.id.repoDescription)
        val repoLanguage: TextView = itemView.findViewById(R.id.repoLanguage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.repoName.text = item.name
        holder.ownerLogin.text = item.owner.login
        holder.repoDescription.text = item.description ?: ""
        holder.repoLanguage.text = item.language ?: ""

        holder.repoName.setOnClickListener { onRepoClick(item.html_url) }
        holder.ownerLogin.setOnClickListener { onOwnerClick(item.owner.html_url) }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<RepositoryItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
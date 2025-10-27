package ru.wladislavshcherbakov.lab15

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShoppingItemDialog.ShoppingItemDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShoppingAdapter
    private val shoppingList = mutableListOf<ShoppingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        setupSwipeToDelete()
        setupFloatingActionButton()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.list)
        adapter = ShoppingAdapter(shoppingList) { item, position ->
            ShoppingItemDialog.newInstance(item, position).show(supportFragmentManager, "edit_dialog")
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSwipeToDelete() {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeItem(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupFloatingActionButton() {
        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            ShoppingItemDialog.newInstance(null, -1).show(supportFragmentManager, "add_dialog")
        }
    }

    override fun onItemAdded(item: ShoppingItem) {
        adapter.addItem(item)
    }

    override fun onItemUpdated(item: ShoppingItem, position: Int) {
        adapter.updateItem(position, item)
    }
}
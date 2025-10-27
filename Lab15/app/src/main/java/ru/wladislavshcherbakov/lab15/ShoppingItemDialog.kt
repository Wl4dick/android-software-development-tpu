package ru.wladislavshcherbakov.lab15

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment

@Suppress("DEPRECATION")
class ShoppingItemDialog : DialogFragment() {

    interface ShoppingItemDialogListener {
        fun onItemAdded(item: ShoppingItem)
        fun onItemUpdated(item: ShoppingItem, position: Int)
    }

    private var listener: ShoppingItemDialogListener? = null
    private var editPosition: Int = -1
    private var editItem: ShoppingItem? = null

    companion object {
        private const val ARG_ITEM = "item"
        private const val ARG_POSITION = "position"

        fun newInstance(item: ShoppingItem?, position: Int): ShoppingItemDialog {
            val args = Bundle().apply {
                putParcelable(ARG_ITEM, item)
                putInt(ARG_POSITION, position)
            }
            return ShoppingItemDialog().apply {
                arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editItem = it.getParcelable(ARG_ITEM)
            editPosition = it.getInt(ARG_POSITION, -1)
        }
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_shopping, null)
        val etName = view.findViewById<EditText>(R.id.etName)
        val etQuantity = view.findViewById<EditText>(R.id.etQuantity)

        editItem?.let {
            etName.setText(it.name)
            etQuantity.setText(it.quantity)
        }

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle(if (editPosition == -1) R.string.add_item else R.string.edit_item)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val name = etName.text.toString()
                val quantity = etQuantity.text.toString()
                if (name.isNotBlank() && quantity.isNotBlank()) {
                    val newItem = ShoppingItem(name = name, quantity = quantity)
                    if (editPosition == -1) {
                        listener?.onItemAdded(newItem)
                    } else {
                        listener?.onItemUpdated(newItem, editPosition)
                    }
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShoppingItemDialogListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ShoppingItemDialogListener")
        }
    }
}
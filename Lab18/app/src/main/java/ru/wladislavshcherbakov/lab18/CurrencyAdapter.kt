package ru.wladislavshcherbakov.lab18

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CurrencyAdapter(private val currencies: List<Currency>) : BaseAdapter() {

    override fun getCount(): Int = currencies.size

    override fun getItem(position: Int): Currency = currencies[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.currency_item, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val currency = getItem(position)
        holder.nameTextView.text = currency.name
        holder.valueTextView.text = currency.value

        return view
    }

    private class ViewHolder(view: View) {
        val nameTextView: TextView = view.findViewById(R.id.currencyName)
        val valueTextView: TextView = view.findViewById(R.id.currencyValue)
    }
}
package com.meowney.ui.entries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meowney.R
import com.meowney.data.database.entities.GeneralTransaction
import com.meowney.data.database.entities.TransactionCategory
import com.meowney.databinding.ItemEntriesEntryBinding

class EntriesAdapter(
    private val items: List<GeneralTransaction>,
    private val categories: List<TransactionCategory> // gets all categories from database beforehand to save performance
) : RecyclerView.Adapter<EntriesAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemEntriesEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = items[position]
        val category = categories.find { it.id == item.categoryId }

        holder.binding.title.text = item.title
        holder.binding.amount.text = "${item.amount}"

        if (item.amount > 0) {
            val color = com.google.android.material.color.MaterialColors.getColor(holder.itemView, R.attr.profitColor)
            holder.binding.amount.setTextColor(color)
        } else if (item.amount < 0) {
            val color = com.google.android.material.color.MaterialColors.getColor(holder.itemView, R.attr.deficitColor)
            holder.binding.amount.setTextColor(color)
        }

        holder.binding.category.text = category?.name

        category?.icon?.let { iconName ->
            val resourceId = R.drawable::class.java.getField(iconName).getInt(null)
            holder.binding.icon.setImageResource(resourceId)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class TransactionViewHolder(val binding: ItemEntriesEntryBinding) : RecyclerView.ViewHolder(binding.root)
}
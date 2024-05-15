package com.dokari4.personalfinance.ui.overview.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemHistoryOverviewBinding
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.util.CurrencyConverter

class CategoryAdapter(private val colors: List<Int>) :
    ListAdapter<CategoryCountTotal, CategoryAdapter.ViewHolder>(ListItemDiffCallback) {

    private object ListItemDiffCallback : DiffUtil.ItemCallback<CategoryCountTotal>() {
        override fun areItemsTheSame(oldItem: CategoryCountTotal, newItem: CategoryCountTotal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryCountTotal, newItem: CategoryCountTotal): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryOverviewBinding.bind(itemView)

        fun bind(category: CategoryCountTotal, position: Int) {
//            val amount = CurrencyConverter.convertToRupiah(transaction.amount.toBigDecimal())

            binding.tvNameCategory.text = category.name
            binding.imgCategory.setColorFilter(this@CategoryAdapter.colors[position])
            binding.tvAmount.text = CurrencyConverter.convertToRupiah(category.amount.toBigDecimal())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_overview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item, position)
    }
}
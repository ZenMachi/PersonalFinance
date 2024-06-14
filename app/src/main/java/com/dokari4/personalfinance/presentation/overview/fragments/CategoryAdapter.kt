package com.dokari4.personalfinance.presentation.overview.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemHistoryOverviewBinding
import com.dokari4.core.domain.model.CategoryCountTotal
import com.dokari4.core.util.CurrencyConverter
import com.dokari4.core.util.enums.CategoryType

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

            when (CategoryType.fromDescription(category.name)) {
                CategoryType.FOOD -> binding.imgCategory.setImageResource(R.drawable.ic_food_24)
                CategoryType.SHOPPING -> binding.imgCategory.setImageResource(R.drawable.ic_shopping_24)
                CategoryType.SUBSCRIPTION -> binding.imgCategory.setImageResource(R.drawable.ic_subscription_24)
                CategoryType.TRANSPORTATION -> binding.imgCategory.setImageResource(R.drawable.ic_transportation_24)
                CategoryType.HEALTH -> binding.imgCategory.setImageResource(R.drawable.ic_health_24)
                CategoryType.EDUCATION -> binding.imgCategory.setImageResource(R.drawable.ic_education_24)
                CategoryType.GIFTS -> binding.imgCategory.setImageResource(R.drawable.ic_gift_24)
            }
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
package com.dokari4.personalfinance.presentation.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.core.util.CurrencyConverter
import com.dokari4.core.util.enums.CategoryType
import com.dokari4.core.util.enums.TransactionType
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemHistoryTransactionBinding
import com.dokari4.personalfinance.presentation.home.state.TransactionListState

class TransactionAdapter :
    ListAdapter<TransactionListState, TransactionAdapter.ViewHolder>(ListItemDiffCallback) {

    private object ListItemDiffCallback : DiffUtil.ItemCallback<TransactionListState>() {
        override fun areItemsTheSame(
            oldItem: TransactionListState,
            newItem: TransactionListState
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionListState,
            newItem: TransactionListState
        ): Boolean {
            return oldItem == newItem
        }

    }

    lateinit var onItemClick: (TransactionListState) -> Unit

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryTransactionBinding.bind(itemView)

        fun bind(transaction: TransactionListState) {

            binding.root.setOnClickListener {
                onItemClick.invoke(transaction)
            }

            val description = itemView.context.getString(
                R.string.text_item_transaction_desc,
                transaction.accountType,
                transaction.date,
                transaction.time
            )
            val amount = CurrencyConverter.convertToRupiah(transaction.amount.toBigDecimal())

            binding.tvNameTransaction.text = transaction.name
            binding.tvAmount.text = amount
            binding.tvNameDescription.text = description

            when (TransactionType.fromDescription(transaction.type)) {
                TransactionType.INCOME -> binding.tvAmount.setTextColor(Color.GREEN)
                TransactionType.EXPENSE -> binding.tvAmount.setTextColor(Color.RED)
            }

            when (CategoryType.fromDescription(transaction.categoryType)) {
                CategoryType.FOOD -> binding.imgCategory.setImageResource(R.drawable.ic_food_24)
                CategoryType.SHOPPING -> binding.imgCategory.setImageResource(R.drawable.ic_shopping_24)
                CategoryType.SUBSCRIPTION -> binding.imgCategory.setImageResource(R.drawable.ic_subscription_24)
                CategoryType.TRANSPORTATION -> binding.imgCategory.setImageResource(R.drawable.ic_transportation_24)
                CategoryType.HEALTH -> binding.imgCategory.setImageResource(R.drawable.ic_health_24)
                CategoryType.EDUCATION -> binding.imgCategory.setImageResource(R.drawable.ic_education_24)
                CategoryType.GIFTS -> binding.imgCategory.setImageResource(R.drawable.ic_gift_24)
                CategoryType.TRANSFER -> binding.imgCategory.setImageResource(R.drawable.ic_transfer_24)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item)
    }
}
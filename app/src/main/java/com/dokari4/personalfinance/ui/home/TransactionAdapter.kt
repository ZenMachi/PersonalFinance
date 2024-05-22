package com.dokari4.personalfinance.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemHistoryTransactionBinding
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.util.CurrencyConverter
import com.dokari4.personalfinance.util.DateConverter

class TransactionAdapter :
    ListAdapter<Transaction, TransactionAdapter.ViewHolder>(ListItemDiffCallback) {

    private object ListItemDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }

    lateinit var onItemClick: (Transaction) -> Unit

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryTransactionBinding.bind(itemView)

        fun bind(transaction: Transaction) {

            binding.root.setOnClickListener {
                onItemClick.invoke(transaction)
            }

            val date = DateConverter.setTimeToDate(transaction.dateTime)
            val time = DateConverter.setTimeToHourAndMinutes(transaction.dateTime)
            val description = "${transaction.type} • $date • $time"
            val amount = CurrencyConverter.convertToRupiah(transaction.amount.toBigDecimal())

            binding.tvNameTransaction.text = transaction.name
            binding.tvAmount.text = amount
            binding.tvNameDescription.text = description
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
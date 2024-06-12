package com.dokari4.personalfinance.presentation.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemHistoryTransactionBinding
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.util.CurrencyConverter
import com.dokari4.personalfinance.util.DateConverter
import com.dokari4.personalfinance.util.enums.CategoryType
import com.dokari4.personalfinance.util.enums.TransactionType

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
    private val listAccounts = ArrayList<Account>()
    private val listCategory = ArrayList<Category>()
    fun setListAccount(list: List<Account>) {
        listAccounts.clear()
        listAccounts.addAll(list)
    }

    fun setListCategory(list: List<Category>) {
        if (listCategory.isNotEmpty()) return
        listCategory.addAll(list)
    }

    lateinit var onItemClick: (Transaction) -> Unit

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryTransactionBinding.bind(itemView)

        fun bind(transaction: Transaction) {

            binding.root.setOnClickListener {
                onItemClick.invoke(transaction)
            }

            val filteredAccount = listAccounts.find { it.id == transaction.accountId }
            val accountType = filteredAccount?.accountType

            val filteredCategory = listCategory.find { it.id == transaction.categoryId }
            val categoryName = filteredCategory?.name

            val date = DateConverter.setTimeToDate(transaction.dateTime)
            val time = DateConverter.setTimeToHourAndMinutes(transaction.dateTime)
            val description = itemView.context.getString(R.string.text_item_transaction_desc, accountType, date, time)
            val amount = CurrencyConverter.convertToRupiah(transaction.amount.toBigDecimal())

            binding.tvNameTransaction.text = transaction.name
            binding.tvAmount.text = amount
            binding.tvNameDescription.text = description

            when (TransactionType.fromDescription(transaction.type)) {
                TransactionType.INCOME -> binding.tvAmount.setTextColor(Color.GREEN)
                TransactionType.EXPENSE -> binding.tvAmount.setTextColor(Color.RED)
            }

            // TODO: Sometimes occur NPE
            if (categoryName != null) {
                when (CategoryType.fromDescription(categoryName)) {
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
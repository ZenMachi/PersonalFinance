package com.dokari4.personalfinance.ui.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemCardAccountBinding
import com.dokari4.personalfinance.domain.model.AccountWithTransactions
import com.dokari4.personalfinance.util.CurrencyConverter

class AccountAdapter :
    ListAdapter<AccountWithTransactions, AccountAdapter.Viewholder>(ListItemDiffCallback) {

    private object ListItemDiffCallback : DiffUtil.ItemCallback<AccountWithTransactions>() {
        override fun areItemsTheSame(
            oldItem: AccountWithTransactions,
            newItem: AccountWithTransactions
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AccountWithTransactions,
            newItem: AccountWithTransactions
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardAccountBinding.bind(itemView)

        /*init {
            binding.root.setOnClickListener {

            }
        }*/

        fun bind(account: AccountWithTransactions) {
            with(binding) {
                val expense = CurrencyConverter.convertToRupiah(account.totalExpense.toBigDecimal())
                val income = CurrencyConverter.convertToRupiah(account.totalIncome.toBigDecimal())
                val amount = CurrencyConverter.convertToRupiah(account.amount.toBigDecimal())

                tvNameAccount.text = account.name
                tvAmount.text = amount
                tvIncomeAmount.text = income
                tvExpenseAmount.text = expense
                when (account.accountType) {
                    "Cash" -> {
                        tvTypeAccount.text = account.accountType
                        imgTypeAccount.setImageResource(R.drawable.ic_account_circle_24)
                    }

                    else -> {
                        tvTypeAccount.text = "Undefined"
                    }
                }
                tvTypeAccount.text = account.accountType
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = getItem(position)
        return holder.bind(item)
    }
}
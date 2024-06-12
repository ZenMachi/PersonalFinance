package com.dokari4.personalfinance.presentation.accounts

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
import com.dokari4.personalfinance.util.enums.AccountType

class AccountAdapter :
    ListAdapter<AccountWithTransactions, AccountAdapter.Viewholder>(ListItemDiffCallback) {

    lateinit var onItemClick: (AccountWithTransactions) -> Unit

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

        fun bind(account: AccountWithTransactions) {
            with(binding) {

                root.setOnClickListener {
                    onItemClick.invoke(account)
                }

                val expense = account.totalExpense.toBigDecimal()
                val income = account.totalIncome.toBigDecimal()
                val amount = account.amount.toBigDecimal() + income - expense

                tvNameAccount.text = account.name
                tvAmount.text = CurrencyConverter.convertToRupiah(amount)
                tvIncomeAmount.text = CurrencyConverter.convertToRupiah(income)
                tvExpenseAmount.text = CurrencyConverter.convertToRupiah(expense)
                when (AccountType.fromDescription(account.accountType)) {
                    AccountType.CASH -> {
                        tvTypeAccount.text = account.accountType
                        imgTypeAccount.setImageResource(R.drawable.ic_cash_24)
                    }

                    AccountType.BANK -> {
                        tvTypeAccount.text = account.accountType
                        imgTypeAccount.setImageResource(R.drawable.ic_bank_24)
                    }
                    AccountType.E_WALLET -> {
                        tvTypeAccount.text = account.accountType
                        imgTypeAccount.setImageResource(R.drawable.ic_credit_card_24)
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
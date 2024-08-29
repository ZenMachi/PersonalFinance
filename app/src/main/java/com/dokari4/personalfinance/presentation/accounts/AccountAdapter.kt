package com.dokari4.personalfinance.presentation.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.core.util.CurrencyConverter
import com.dokari4.core.util.enums.AccountType
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemCardAccountBinding
import com.dokari4.personalfinance.presentation.accounts.state.AccountListState

class AccountAdapter :
    ListAdapter<AccountListState, AccountAdapter.Viewholder>(ListItemDiffCallback) {

    lateinit var onItemClick: (AccountListState) -> Unit

    private object ListItemDiffCallback : DiffUtil.ItemCallback<AccountListState>() {
        override fun areItemsTheSame(
            oldItem: AccountListState,
            newItem: AccountListState
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AccountListState,
            newItem: AccountListState
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardAccountBinding.bind(itemView)

        fun bind(account: AccountListState) {
            with(binding) {

                root.setOnClickListener {
                    onItemClick.invoke(account)
                }

                tvNameAccount.text = account.name
                tvAmount.text = CurrencyConverter.convertToRupiah(account.balance)
                tvIncomeAmount.text = CurrencyConverter.convertToRupiah(account.income)
                tvExpenseAmount.text = CurrencyConverter.convertToRupiah(account.expense)
                when (AccountType.fromDescription(account.type)) {
                    AccountType.CASH -> {
                        tvTypeAccount.text = account.type
                        imgTypeAccount.setImageResource(R.drawable.ic_cash_24)
                    }

                    AccountType.BANK -> {
                        tvTypeAccount.text = account.type
                        imgTypeAccount.setImageResource(R.drawable.ic_bank_24)
                    }
                    AccountType.E_WALLET -> {
                        tvTypeAccount.text = account.type
                        imgTypeAccount.setImageResource(R.drawable.ic_credit_card_24)
                    }
                }
                tvTypeAccount.text = account.type
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
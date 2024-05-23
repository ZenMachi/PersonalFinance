package com.dokari4.personalfinance.ui.add_transaction

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemCardAccountAddBinding
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.util.enums.AccountType

class AccountAdapter(private val callback: (id: Int) -> Unit) :
    RecyclerView.Adapter<AccountAdapter.Viewholder>() {

    private val listData = ArrayList<Account>()
    var onItemClick: ((Account) -> Unit)? = null
    private var selectedPosition = -1

    fun setData(newListData: List<Account>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun setPosition(position: Int?) {
        if (position == null) return
        selectedPosition = position - 1
        notifyDataSetChanged()
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardAccountAddBinding.bind(itemView)

        /*init {
            binding.root.setOnClickListener {

            }
        }*/

        fun bind(data: Account, position: Int) {
            with(binding) {
                root.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                    Log.d("Click check", "Selected position: $selectedPosition, position: $position")
                }
                tvNameAccount.text = data.name
                when (AccountType.fromDescription(data.accountType)) {
                    AccountType.CASH -> binding.imgTypeAccount.setImageResource(R.drawable.ic_cash_24)
                    AccountType.BANK -> binding.imgTypeAccount.setImageResource(R.drawable.ic_bank_24)
                    AccountType.E_WALLET -> binding.imgTypeAccount.setImageResource(R.drawable.ic_credit_card_24)
                }
                tvTypeAccount.text = data.accountType
                if (selectedPosition == position) {
                    callback(data.id!!)
                    layoutItem.setBackgroundResource(R.drawable.bg_stroke)
                } else
                    layoutItem.setBackgroundResource(0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder =
        Viewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_account_add, parent, false)
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val data = listData[position]
        holder.bind(data, position)
    }

}
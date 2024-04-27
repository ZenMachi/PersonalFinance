package com.dokari4.personalfinance.ui.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemCardAccountBinding
import com.dokari4.personalfinance.domain.model.Account

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.Viewholder>() {

    private var listData = ArrayList<Account>()
    var onItemClick: ((Account) -> Unit)? = null

    fun setData(newListData: List<Account>?) {
        if (newListData.isNullOrEmpty()) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardAccountBinding.bind(itemView)

        /*init {
            binding.root.setOnClickListener {

            }
        }*/

        fun bind(data: Account) {
            with(binding) {
                tvNameAccount.text = data.name
                tvAmount.text = data.amount.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder =
        Viewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_card_account, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }
}
package com.dokari4.personalfinance.ui.add_transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ItemCardAccountAddBinding
import com.dokari4.personalfinance.domain.model.Account

class AccountAdapter(private val callback: (id: Int) -> Unit) :
    ListAdapter<Account, AccountAdapter.Viewholder>(ListItemDiffCallback) {

    private object ListItemDiffCallback : DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem == newItem
        }

    }

    private var selectedPosition = -1

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
                }
                tvNameAccount.text = data.name
                when (data.accountType) {
                    "Cash" -> {
                        tvTypeAccount.text = data.accountType
                        imgTypeAccount.setImageResource(R.drawable.ic_account_circle_24)
                    }

                    else -> {
                        tvTypeAccount.text = "Undefined"
                    }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_account_add, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

}
package com.dokari4.personalfinance.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.core.util.CurrencyConverter
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.FragmentHomeBinding
import com.dokari4.personalfinance.presentation.add_transaction.AddTransactionActivity
import com.dokari4.personalfinance.presentation.update_transaction.UpdateTransactionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val transactionAdapter: TransactionAdapter by lazy {
        TransactionAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderUIFromViewModel()

        binding.rvTransaction.apply {
            adapter = transactionAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        transactionAdapter.onItemClick = {
            val intent = Intent(context, UpdateTransactionActivity::class.java)
            intent.putExtra(UpdateTransactionActivity.EXTRA_TRANSACTION, it.data)
            intent.putExtra(UpdateTransactionActivity.EXTRA_BALANCE, viewModel.balanceMoney.value)
            Log.d("HomeFragment", "TransactionAdapter: $it")
            startActivity(intent)
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(context, AddTransactionActivity::class.java)
            intent.putExtra(AddTransactionActivity.EXTRA_BALANCE, viewModel.balanceMoney.value)
            startActivity(intent)
        }
    }

    private fun renderUIFromViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    binding.tvTitle.text = getString(R.string.text_hello_user, it.username)
                    binding.tvBalance.text =
                        CurrencyConverter.convertToRupiah(it.balanceMoney.toBigDecimal())
                    binding.layoutThisMonth.tvIncomeAmount.text =
                        CurrencyConverter.convertToRupiah(it.totalIncome.toBigDecimal())
                    binding.layoutThisMonth.tvExpenseAmount.text =
                        CurrencyConverter.convertToRupiah(it.totalExpense.toBigDecimal())
                    binding.tvNoTransaction.visibility =
                        if (it.isEmpty == true) View.VISIBLE else View.GONE
                    binding.rvTransaction.visibility =
                        if (it.isEmpty == true) View.GONE else View.VISIBLE
                    transactionAdapter.submitList(it.transactions)
                }
            }
        }
    }

}
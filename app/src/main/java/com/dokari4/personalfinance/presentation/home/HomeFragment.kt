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
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.FragmentHomeBinding
import com.dokari4.personalfinance.presentation.add_transaction.AddTransactionActivity
import com.dokari4.personalfinance.presentation.update_transaction.UpdateTransactionActivity
import com.dokari4.core.util.CurrencyConverter
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUserName.collect {
                    binding.tvTitle.text = getString(R.string.text_hello_user, it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isContentEmpty().collect {
                    binding.tvNoTransaction.visibility = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategories.collect {
                    transactionAdapter.setListCategory(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAccounts.collect {
                    transactionAdapter.setListAccount(it)
                }
            }
        }




        binding.fabAdd.setOnClickListener {
            val intent = Intent(context, AddTransactionActivity::class.java)
            startActivity(intent)
        }

        binding.rvTransaction.apply {
            adapter = transactionAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        transactionAdapter.onItemClick = {
            val intent = Intent(context, UpdateTransactionActivity::class.java)
            intent.putExtra(UpdateTransactionActivity.EXTRA_TRANSACTION, it)
            Log.d("HomeFragment", "TransactionAdapter: $it")
            startActivity(intent)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAccountsWithTransactions.collect { accounts ->
                    val totalIncome = accounts.sumOf { account ->
                        account.totalIncome
                    }
                    val totalExpense = accounts.sumOf { account ->
                        account.totalExpense
                    }
                    val amount = accounts.sumOf { account ->
                        account.amount
                    }
                    val balance = amount + totalIncome - totalExpense

                    with(binding) {
                        tvBalance.text = CurrencyConverter.convertToRupiah(balance.toBigDecimal())
                        layoutThisMonth.tvIncomeAmount.text =
                            CurrencyConverter.convertToRupiah(totalIncome.toBigDecimal())
                        layoutThisMonth.tvExpenseAmount.text =
                            CurrencyConverter.convertToRupiah(totalExpense.toBigDecimal())
                    }
                    Log.d("HomeFragment", "onViewCreated: $totalIncome")
                    Log.d("HomeFragment", "onViewCreated: $totalExpense")
                    Log.d("HomeFragment", "onViewCreated: $amount")
                    Log.d("HomeFragment", "onViewCreated: $accounts")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTransactions.collect {
                    transactionAdapter.submitList(it)
//            if (it.isNotEmpty()) {
//                binding.tvNoTransaction.visibility = View.GONE
//            } else {
//                binding.tvNoTransaction.visibility = View.VISIBLE
//                binding.rvTransaction.visibility = View.GONE
//            }

                }
            }
        }




    }

}
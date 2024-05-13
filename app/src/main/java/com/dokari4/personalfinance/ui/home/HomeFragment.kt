package com.dokari4.personalfinance.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.FragmentHomeBinding
import com.dokari4.personalfinance.ui.add_transaction.AddTransactionActivity
import com.dokari4.personalfinance.ui.onboarding.OnboardingActivity
import com.dokari4.personalfinance.util.CurrencyConverter
import com.dokari4.personalfinance.util.OnboardingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionAdapter = TransactionAdapter()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(context, AddTransactionActivity::class.java)
            startActivity(intent)
        }

        binding.rvTransaction.apply {
            adapter = transactionAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.getAccountsWithTransactions.observe(viewLifecycleOwner) { accounts ->
            val totalIncome = accounts.sumOf { account ->
                account.totalIncome
            }
            val totalExpense = accounts.sumOf { account ->
                account.totalExpense
            }
            val amount = accounts.sumOf { account ->
                account.amount + totalIncome - totalExpense
            }

            with(binding) {
                tvBalance.text = CurrencyConverter.convertToRupiah(amount.toBigDecimal())
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


        viewModel.getTransactions.observe(viewLifecycleOwner) {
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
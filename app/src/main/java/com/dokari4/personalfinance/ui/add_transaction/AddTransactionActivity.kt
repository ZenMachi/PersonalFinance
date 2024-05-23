package com.dokari4.personalfinance.ui.add_transaction

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ActivityAddTransactionBinding
import com.dokari4.personalfinance.ui.add_transaction.income_expense.IncomeExpenseFragment
import com.dokari4.personalfinance.ui.add_transaction.transfer.TransferFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddTransactionActivity : AppCompatActivity() {
    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(IncomeExpenseFragment())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValidIncomeExpense().collect {
                    binding.btnAdd.isEnabled = it
                    Log.d("AddTransactionActivity", "isValid: $it")
                }
            }
        }

        binding.chipGroupTransaction.apply {
            setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.isNotEmpty()) {
                    val chip: Chip = findViewById(checkedIds.first())
                    val selection = chip.text.toString()
                    viewModel.updateTransactionType(selection)
                    when (checkedIds.first()) {
                        binding.chipIncome.id -> {
                            setCurrentFragment(IncomeExpenseFragment())
                            binding.btnAdd.text = getString(R.string.text_btn_add)

                            lifecycleScope.launch {
                                repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    viewModel.isValidIncomeExpense().collect {
                                        binding.btnAdd.isEnabled = it
                                        Log.d("AddTransactionActivity", "isValid: $it")
                                    }
                                }
                            }

                            binding.btnAdd.apply {
                                setOnClickListener {
                                    lifecycleScope.launch {
                                        viewModel.insertTransaction().await()
                                        finish()
                                    }
                                }
                            }

                        }

                        binding.chipExpense.id -> {
                            setCurrentFragment(IncomeExpenseFragment())
                            binding.btnAdd.text = getString(R.string.text_btn_add)

                            lifecycleScope.launch {
                                repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    viewModel.isValidIncomeExpense().collect {
                                        binding.btnAdd.isEnabled = it
                                        Log.d("AddTransactionActivity", "isValid: $it")
                                    }
                                }
                            }

                            binding.btnAdd.apply {
                                setOnClickListener {
                                    lifecycleScope.launch {
                                        viewModel.insertTransaction().await()
                                        finish()
                                    }
                                }
                            }
                        }

                        binding.chipTransfer.id -> {
                            setCurrentFragment(TransferFragment())
                            binding.btnAdd.text = getString(R.string.text_transfer)

                            lifecycleScope.launch {
                                repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    viewModel.isValidTransfer().collect {
                                        binding.btnAdd.isEnabled = it
                                        Log.d("AddTransactionActivity", "isValid: $it")
                                    }
                                }
                            }

                            binding.btnAdd.apply {
                                setOnClickListener {
                                    lifecycleScope.launch {
                                        viewModel.getAccounts.collect {
                                            viewModel.transferTransaction(it).await()
                                            finish()
                                        }

                                    }
                                }
                            }
                        }
                    }
                } else {
                    viewModel.updateTransactionType("")
                }
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.navHostFragment.id, fragment)
            .commit()
    }
}
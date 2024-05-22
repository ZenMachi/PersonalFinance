package com.dokari4.personalfinance.ui.update_account

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dokari4.personalfinance.databinding.ActivityUpdateAccountBinding
import com.dokari4.personalfinance.domain.model.AccountWithTransactions
import com.dokari4.personalfinance.util.enums.AccountType
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAccountBinding
    private val viewModel: UpdateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(
                ACCOUNT_WITH_TRANSACTIONS,
                AccountWithTransactions::class.java
            )
        } else {
            intent.getParcelableExtra(ACCOUNT_WITH_TRANSACTIONS)
        }

        updateValueToViewModel(extras!!)
        setTextFromViewModel()
        setupChipGroup()
        addEditTextListener()



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValid().collect {
                    binding.btnAdd.isEnabled = it
                }
            }
        }

        binding.btnAdd.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateAccount().await()
                finish()
            }
        }
    }

    private fun addEditTextListener() {
        binding.inputName.addTextChangedListener {
            viewModel.updateEditTextName(it.toString())
        }
        binding.inputAmount.addTextChangedListener {
            viewModel.updateEditTextAmount(it.toString())
        }
    }

    private fun setupChipGroup() {
        binding.chipGroupTransaction.apply {
            when (AccountType.fromDescription(viewModel.selectionType.value)) {
                AccountType.CASH -> check(binding.chipCash.id)
                AccountType.BANK -> check(binding.chipBank.id)
                AccountType.E_WALLET -> check(binding.chipEWallet.id)
            }

            setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.isNotEmpty()) {
                    val chip: Chip = findViewById(checkedIds.first())
                    val selection = chip.text.toString()
                    viewModel.updateSelectionType(selection)
                } else {
                    viewModel.updateSelectionType("")
                }
//                for (id in checkedIds) {
//                    val chip: Chip = findViewById(id)
//                    viewModel.selectionType.value = chip.text.toString()
//                    Log.d("SELECTION", chip.text.toString())
//                }
            }
        }
    }

    private fun setTextFromViewModel() {
        binding.inputName.setText(viewModel.name.value)
        binding.inputAmount.setText(viewModel.amount.value.toBigDecimal().toPlainString())
    }

    private fun updateValueToViewModel(extras: AccountWithTransactions) {
        viewModel.updateEditTextName(extras.name)
        viewModel.updateEditTextAmount(extras.amount.toString())
        viewModel.updateSelectionType(extras.accountType)
        viewModel.updateAccountId(extras.id)
    }

    companion object {
        const val ACCOUNT_WITH_TRANSACTIONS = "ACCOUNT_WITH_TRANSACTIONS"
    }
}
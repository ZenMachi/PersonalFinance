package com.dokari4.personalfinance.presentation.update_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ActivityUpdateTransactionBinding
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.util.DateConverter
import com.dokari4.personalfinance.util.enums.CategoryType
import com.dokari4.personalfinance.util.enums.TransactionType
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class UpdateTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTransactionBinding
    private val viewModel: UpdateTransactionViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private val accountAdapter: AccountAdapter by lazy {
        AccountAdapter {
            viewModel.updateAccountId(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(
                EXTRA_TRANSACTION,
                Transaction::class.java
            )
        } else {
            intent.getParcelableExtra(EXTRA_TRANSACTION)
        }

        binding.rvAccounts.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = accountAdapter
        }



        updateValueToViewModel(extras)
        addAccountsToRV()
        addCategoriesToChip()
        addListenerValue()

        setValueFromViewModel()


        binding.btnTime.apply {
            setOnClickListener {
                showClock()
            }
        }
        binding.btnDate.apply {
            setOnClickListener {
                showDate()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValid().collect {
                    binding.btnAdd.isEnabled = it
                }
            }
        }

        binding.btnAdd.apply {
//            text = DateConverter.setDateAndTimeToLong(
//                viewModel.date.value,
//                viewModel.time.value
//            ).toString()
//            isEnabled = false
//            setOnClickListener {
//                val name = binding.inputName.text.toString()
//                val description = binding.inputDesc.text.toString()
//                val amount = binding.inputAmount.text.toString()
//                val date = binding.btnDate.text.toString()
//                val time = binding.btnTime.text.toString()
//            }
            setOnClickListener {
                lifecycleScope.launch {
                    viewModel.updateTransaction().await()
                    finish()
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteTransaction().await()
                finish()
            }
        }

    }

    private fun setValueFromViewModel() {
        accountAdapter.setAccountId(viewModel.accountId.value!!)
        Log.d("ACCOUNT_ID", viewModel.accountId.value.toString())

        binding.inputName.setText(viewModel.name.value)
        binding.inputDesc.setText(viewModel.description.value)
        binding.inputAmount.setText(viewModel.amount.value)

        binding.btnTime.text = viewModel.time.value
        binding.btnDate.text = viewModel.date.value

        binding.chipGroupTransaction.apply {
            when (TransactionType.fromDescription(viewModel.transactionType.value)) {
                TransactionType.INCOME -> check(binding.chipIncome.id)
                TransactionType.EXPENSE -> check(binding.chipExpense.id)
            }
        }
    }

    private fun addListenerValue() {
        binding.inputName.addTextChangedListener {
            viewModel.updateEditTextName(it.toString())
        }

        binding.inputDesc.addTextChangedListener {
            viewModel.updateEditTextDescription(it.toString())
        }

        binding.inputAmount.addTextChangedListener {
            viewModel.updateEditTextAmount(it.toString())
        }

        binding.btnTime.addTextChangedListener {
            viewModel.updateButtonTimeValue(it.toString())
        }

        binding.btnDate.addTextChangedListener {
            viewModel.updateButtonDateValue(it.toString())
        }

        binding.chipGroupTransaction.apply {
            setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.isNotEmpty()) {
                    val chip: Chip = findViewById(checkedIds.first())
                    val selection = chip.text.toString()
                    viewModel.updateTransactionType(selection)
                } else {
                    viewModel.updateTransactionType("")
                }
            }
        }
        binding.chipGroupCategory.apply {
            setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.isNotEmpty()) {
                    val chip: Chip = findViewById(checkedIds.first())
                    val selectionId = chip.id
                    viewModel.updateCategory(selectionId)
                } else {
                    viewModel.updateCategory(null)
                }
            }
        }
    }

    private fun addCategoriesToChip() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategories.collect { categories ->
                    categories.forEach { category ->
                        val chip = layoutInflater.inflate(
                            R.layout.item_chip_category,
                            binding.chipGroupCategory,
                            false
                        ) as Chip
                        chip.apply {
                            if (category.id == viewModel.categoryId.value) {
                                isChecked = true
                            }
                            id = category.id!!
                            text = category.name
                            chipIcon = when (CategoryType.fromDescription(category.name)) {
                                CategoryType.FOOD -> AppCompatResources.getDrawable(
                                    context,
                                    R.drawable.ic_food_24
                                )

                                CategoryType.SHOPPING -> AppCompatResources.getDrawable(
                                    context,
                                    R.drawable.ic_shopping_24
                                )

                                CategoryType.SUBSCRIPTION -> AppCompatResources.getDrawable(
                                    context,
                                    R.drawable.ic_subscription_24
                                )

                                CategoryType.TRANSPORTATION -> AppCompatResources.getDrawable(
                                    context,
                                    R.drawable.ic_transportation_24
                                )

                                CategoryType.HEALTH -> AppCompatResources.getDrawable(
                                    context,
                                    R.drawable.ic_health_24
                                )

                                CategoryType.EDUCATION -> AppCompatResources.getDrawable(
                                    context,
                                    R.drawable.ic_education_24
                                )

                                CategoryType.GIFTS -> AppCompatResources.getDrawable(
                                    context,
                                    R.drawable.ic_gift_24
                                )

                            }
                        }
                        binding.chipGroupCategory.addView(chip)
                    }
//                    for (category in categories) {
//    //                        val chipDrawable = ChipDrawable.createFromAttributes(
//    //                            this@UpdateTransactionActivity,
//    //                            null,
//    //                            0,
//    //                            com.google.android.material.R.style.Widget_Material3_Chip_Filter
//    //                        )
//                        val chip = layoutInflater.inflate(
//                            R.layout.item_chip_category,
//                            binding.chipGroupCategory,
//                            false
//                        ) as Chip
//                        chip.apply {
//                            id = category.id!!
//                            text = category.name
//                        }
//    //                val chip = Chip(this).apply {
//    //                    id = category.id!!
//    //                    text = category.name
//    //                    chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_add_24)
//    //                    isChipIconVisible = true
//    //                    isCheckedIconVisible = false
//    //                    setChipDrawable(chipDrawable)
//    //                }
//                        binding.chipGroupCategory.addView(chip)
//                    }
                }
            }
        }
    }

    private fun addAccountsToRV() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAccounts.collect {
                    accountAdapter.setData(it)
                    Log.d("ACCOUNT", it.toString())
                }
            }
        }
    }

    private fun updateValueToViewModel(extras: Transaction?) {
        viewModel.updateButtonDateValue(DateConverter.setTimeToDate(extras!!.dateTime))
        viewModel.updateButtonTimeValue(DateConverter.setTimeToHourAndMinutes(extras.dateTime))
        viewModel.updateEditTextName(extras.name)
        viewModel.updateEditTextDescription(extras.description)
        viewModel.updateEditTextAmount(extras.amount.toBigDecimal().toPlainString())
        viewModel.updateTransactionType(extras.type)
        viewModel.updateCategory(extras.categoryId)
        viewModel.updateAccountId(extras.accountId)
        viewModel.updateTransactionId(extras.id)
    }

    private fun showDate() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val formattedDate = DateConverter.formatDate(calendar.time)
                binding.btnDate.text = formattedDate

                Log.d("DATE", formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }

    private fun showClock() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                val formattedTime = DateConverter.formatTime(calendar.time)
                binding.btnTime.text = formattedTime

                Log.d("TIME", formattedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    companion object {
        const val EXTRA_TRANSACTION = "EXTRA_TRANSACTION"
    }
}
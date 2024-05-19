package com.dokari4.personalfinance.ui.add_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ActivityAddTransactionBinding
import com.dokari4.personalfinance.util.DateConverter
import com.google.android.material.R.style.Widget_Material3_Chip_Filter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AddTransactionActivity : AppCompatActivity(), TextWatcher {
    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var binding: ActivityAddTransactionBinding
    private val calendar = Calendar.getInstance()
    private val accountAdapter: AccountAdapter by lazy {
        AccountAdapter {
            viewModel.updateAccountId(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.updateDate(DateConverter.formatDate(calendar.time))
        viewModel.updateTime(DateConverter.formatTime(calendar.time))

        binding.inputName.addTextChangedListener {
            viewModel.addEditTextNameListener(it)
        }

        binding.inputDesc.addTextChangedListener {
            viewModel.addEditTextDescriptionListener(it)
        }

        binding.inputAmount.addTextChangedListener {
            viewModel.addEditTextAmountListener(it)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAccounts.collect {
                    accountAdapter.setData(it)
                    Log.d("ACCOUNT", it.toString())
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategories.collect {
                    for (category in it) {
                        val chipDrawable = ChipDrawable.createFromAttributes(
                            this@AddTransactionActivity,
                            null,
                            0,
                            Widget_Material3_Chip_Filter
                        )
                        val chip = layoutInflater.inflate(
                            R.layout.item_chip_category,
                            binding.chipGroupCategory,
                            false
                        ) as Chip
                        chip.apply {
                            id = category.id!!
                            text = category.name
                        }
//                val chip = Chip(this).apply {
//                    id = category.id!!
//                    text = category.name
//                    chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_add_24)
//                    isChipIconVisible = true
//                    isCheckedIconVisible = false
//                    setChipDrawable(chipDrawable)
//                }
                        binding.chipGroupCategory.addView(chip)
                    }
                }
            }
        }



        binding.rvAccounts.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = accountAdapter
        }

        binding.btnTime.apply {
            text = viewModel.time.value
            addTextChangedListener {
                viewModel.addButtonTimeListener(it)
            }
            setOnClickListener {
                showClock()
            }
        }
        binding.btnDate.apply {
            text = viewModel.date.value
            addTextChangedListener {
                viewModel.addButtonDateListener(it)
            }
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
                    viewModel.insertTransactionTest().await()
                    finish()
                }
            }
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

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val edtName = binding.inputName.text.toString()
        val edtDesc = binding.inputDesc.text.toString()
        val edtAmount = binding.inputAmount.text.toString()
        binding.btnAdd.isEnabled =
            edtName.isNotEmpty() && edtDesc.isNotEmpty() && edtAmount.isNotEmpty()
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
        val time = Calendar.getInstance()
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
}
package com.dokari4.personalfinance.ui.add_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.DatePicker
import androidx.activity.viewModels
import com.dokari4.personalfinance.databinding.ActivityAddTransactionBinding
import com.dokari4.personalfinance.util.DateConverter
import com.google.android.material.chip.Chip
import java.util.Calendar

class AddTransactionActivity : AppCompatActivity(), TextWatcher {
    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var binding: ActivityAddTransactionBinding
    private val calendar = Calendar.getInstance()

    private var selectionTransaction = ""
    private var selectionCategory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.inputName
        val description = binding.inputDesc
        val amount = binding.inputAmount

        name.addTextChangedListener(this)
        description.addTextChangedListener(this)
        amount.addTextChangedListener(this)

        val currentTime = System.currentTimeMillis()

        val formattedTime = DateConverter.setTimeToHourAndMinutes(currentTime)
        val formattedDate = DateConverter.setTimeToDate(currentTime)
        val convertedDateAndTime =
            DateConverter.setDateAndTimeToLong(date = formattedDate, time = formattedTime)

        binding.btnTime.apply {
            text = formattedTime
            setOnClickListener {
                showClock()
            }
        }
        binding.btnDate.apply {
            text = formattedDate
            setOnClickListener {
                showDate()
            }
        }

        binding.btnAdd.isEnabled = false
        binding.btnAdd.text = convertedDateAndTime.toString()

        binding.chipGroupTransaction.apply {
            check(this.getChildAt(0).id)
            setOnCheckedStateChangeListener { chipGroup, checkedIds ->
                for (id in checkedIds) {
                    val chip: Chip = findViewById(id)
                    selectionTransaction = chip.text.toString()
                }
            }
        }
        binding.chipGroupCategory.apply {
            check(this.getChildAt(0).id)
            setOnCheckedStateChangeListener { chipGroup, checkedIds ->
                for (id in checkedIds) {
                    val chip: Chip = findViewById(id)
                    selectionCategory = chip.text.toString()
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
        binding.btnAdd.isEnabled = edtName.isNotEmpty() && edtDesc.isNotEmpty() && edtAmount.isNotEmpty()
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
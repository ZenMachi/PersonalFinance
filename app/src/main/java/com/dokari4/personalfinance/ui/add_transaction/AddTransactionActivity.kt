package com.dokari4.personalfinance.ui.add_transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ActivityAddTransactionBinding
import com.dokari4.personalfinance.util.DateConverter

class AddTransactionActivity : AppCompatActivity() {
    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var binding: ActivityAddTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.inputName.text
        val description = binding.inputDesc.text
        val amount = binding.inputAmount.text

        val currentTime = System.currentTimeMillis()
        val formattedTime = DateConverter.setTimeToHourAndMinutes(currentTime)
        val formattedDate = DateConverter.setTimeToDate(currentTime)
        val convertedDateAndTime =  DateConverter.setDateAndTimeToLong(date = formattedDate, time = formattedTime)

        binding.btnTime.text = formattedTime
        binding.btnDate.text = formattedDate
        binding.btnAdd.text = convertedDateAndTime.toString()
    }
}
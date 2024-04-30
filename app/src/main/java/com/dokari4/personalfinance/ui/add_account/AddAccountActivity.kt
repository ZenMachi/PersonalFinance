package com.dokari4.personalfinance.ui.add_account

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dokari4.personalfinance.databinding.ActivityAddAccountBinding
import com.dokari4.personalfinance.domain.model.Account
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAccountActivity : AppCompatActivity(), TextWatcher {
    private val viewModel: AddAccountViewModel by viewModels()
    private lateinit var binding: ActivityAddAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.inputName.text
        val amount = binding.inputAmount.text
        var selection = ""

        binding.chipGroupTransaction.apply {
            setOnCheckedStateChangeListener { chipGroup, checkedIds ->
                for (id in checkedIds) {
                    val chip: Chip = findViewById(id)
                    selection = chip.text.toString()
                }
            }
        }

        binding.inputName.addTextChangedListener(this)
        binding.inputAmount.addTextChangedListener(this)
        binding.btnAdd.isEnabled = false

        binding.btnAdd.setOnClickListener {
            val account = Account(
                id = 0,
                userId = 1,
                accountType = selection,
                name = name.toString(),
                amount = amount.toString().toDouble()
            )
            val dummy = Account(
                id = 0,
                userId = 1,
                accountType = "Bank",
                name = "Angelica Carson",
                amount = 4.5
            )
            viewModel.insertAccount(account)

        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val edtName = binding.inputName.text.toString()
        val edtAmount = binding.inputAmount.text.toString()
        binding.btnAdd.isEnabled = edtName.isNotEmpty() && edtAmount.isNotEmpty()
    }
}
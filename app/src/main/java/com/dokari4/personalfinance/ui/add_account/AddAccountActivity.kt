package com.dokari4.personalfinance.ui.add_account

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ActivityAddAccountBinding
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.util.DateConverter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAccountActivity : AppCompatActivity() {
    private val viewModel: AddAccountViewModel by viewModels()
    private lateinit var binding: ActivityAddAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.inputName.text
        val amount = binding.inputAmount.text
        var selection: String = ""

        binding.chipGroupTransaction.apply {
            check(this.getChildAt(0).id)
            setOnCheckedStateChangeListener { chipGroup, checkedIds ->
                for (id in checkedIds) {
                    val chip: Chip = findViewById(id)
                    selection = chip.text.toString()
                }
            }
        }
//        binding.chipGroupTransaction.setOnCheckedStateChangeListener { chipGroup, checkedIds ->
//            for (id in checkedIds) {
//                val chip: Chip = findViewById(id)
//                selection = chip.text.toString()
//            }
//        }

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
}
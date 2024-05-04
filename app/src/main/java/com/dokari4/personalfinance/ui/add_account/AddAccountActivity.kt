package com.dokari4.personalfinance.ui.add_account

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.dokari4.personalfinance.databinding.ActivityAddAccountBinding
import com.dokari4.personalfinance.domain.model.Account
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAccountActivity : AppCompatActivity(), TextWatcher {
    private val viewModel: AddAccountViewModel by viewModels()
    private lateinit var binding: ActivityAddAccountBinding
    private var selection: String? = null
    private var formComplete: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputName.addTextChangedListener { editable ->
            viewModel.addEditTextNameListener(editable)
        }
        binding.inputAmount.addTextChangedListener { editable ->
            viewModel.addEditTextAmountListener(editable)
        }

        binding.chipGroupTransaction.apply {
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

        lifecycleScope.launch {
            viewModel.isValid().collect {
                binding.btnAdd.isEnabled = it
            }
        }

        binding.btnAdd.setOnClickListener {
            val account = Account(
                userId = 1,
                accountType = viewModel.selectionType.value,
                name = viewModel.name.value,
                amount = viewModel.amount.value.toDouble()
            )
            viewModel.insertAccount(account)
        }

//        val chipGroup = binding.chipGroupTransaction
//        val selectedChips = chipGroup.children
//            .filter { (it as Chip).isChecked }
//            .map { (it as Chip).text.toString() }
//            .first(

//        binding.btnAdd.isEnabled = callback(callback(
//
//        ))
//        binding.btnAdd.isEnabled = viewModel.checkFormComplete(
//            name = binding.inputName.text.toString(),
//            amount = binding.inputAmount.text.toString(),
//            type = selection!!
//        )

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val edtName = binding.inputName.text.toString()
        val edtAmount = binding.inputAmount.text.toString()
        if (edtName.isEmpty()) binding.inputName.error = "Name is required"
        if (edtAmount.isEmpty()) binding.inputName.error = "Amount is required"
//        getValidation(name = edtName, amount = edtAmount, type = selection)
//        if (edtName.isNotEmpty() && edtAmount.isNotEmpty() && selection != null) {
//            binding.btnAdd.isEnabled = true
//            binding.btnAdd.setOnClickListener {
//                val account = Account(
//                    userId = 1,
//                    accountType = selection!!,
//                    name = edtName,
//                    amount = edtAmount.toDouble()
//                )
//                viewModel.insertAccount(account)
//            }
//        }
    }

    private fun getValidation(
        name: String?,
        amount: String?,
        type: String?,
        callback: (name: String?, amount: String?, type: String?) -> Unit
    ) {
        return callback(name, amount, type)
    }
}

typealias FormCallback = (name: String, amount: String) -> Unit
typealias ChipsCallback = (String) -> Unit
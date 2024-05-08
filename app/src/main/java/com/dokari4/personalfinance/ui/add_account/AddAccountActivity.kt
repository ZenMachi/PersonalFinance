package com.dokari4.personalfinance.ui.add_account

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.dokari4.personalfinance.databinding.ActivityAddAccountBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAccountActivity : AppCompatActivity() {
    private val viewModel: AddAccountViewModel by viewModels()
    private lateinit var binding: ActivityAddAccountBinding

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
            viewModel.insertAccountTest()
            finish()
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
}
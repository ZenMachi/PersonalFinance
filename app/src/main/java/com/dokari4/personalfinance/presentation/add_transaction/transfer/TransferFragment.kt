package com.dokari4.personalfinance.presentation.add_transaction.transfer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.FragmentTransferBinding
import com.dokari4.personalfinance.presentation.add_transaction.AccountAdapter
import com.dokari4.personalfinance.presentation.add_transaction.AddTransactionViewModel
import com.dokari4.core.util.DateConverter
import com.dokari4.core.util.enums.CategoryType
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddTransactionViewModel by activityViewModels()
    private val accountAdapterStart: AccountAdapter by lazy {
        AccountAdapter {
            viewModel.updateAccountIdStart(it)
        }
    }
    private val accountAdapterEnd: AccountAdapter by lazy {
        AccountAdapter {
            viewModel.updateAccountIdDest(it)
        }
    }
    private val calendar = Calendar.getInstance()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateDate(DateConverter.formatDate(calendar.time))
        viewModel.updateTime(DateConverter.formatTime(calendar.time))

        setValueFromViewModel()


        binding.inputAmount.addTextChangedListener {
            viewModel.addEditTextAmountListener(it)
        }

        binding.rvAccountsStart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = accountAdapterStart
        }

        binding.rvAccountsEnd.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = accountAdapterEnd
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAccounts.collect {
                    accountAdapterStart.setData(it)
                    accountAdapterEnd.setData(it)
                    Log.d("ACCOUNT", it.toString())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategories.collect { categories ->
                    val filteredCategories = categories.filter {
                        it.name == CategoryType.TRANSFER.toString()
                    }
                    filteredCategories.forEach { category ->
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
                                CategoryType.FOOD -> AppCompatResources.getDrawable(context, R.drawable.ic_food_24)
                                CategoryType.SHOPPING -> AppCompatResources.getDrawable(context, R.drawable.ic_shopping_24)
                                CategoryType.SUBSCRIPTION -> AppCompatResources.getDrawable(context, R.drawable.ic_subscription_24)
                                CategoryType.TRANSPORTATION -> AppCompatResources.getDrawable(context, R.drawable.ic_transportation_24)
                                CategoryType.HEALTH -> AppCompatResources.getDrawable(context, R.drawable.ic_health_24)
                                CategoryType.EDUCATION -> AppCompatResources.getDrawable(context, R.drawable.ic_education_24)
                                CategoryType.GIFTS -> AppCompatResources.getDrawable(context, R.drawable.ic_gift_24)
                                CategoryType.TRANSFER -> AppCompatResources.getDrawable(context, R.drawable.ic_transfer_24)
                            }
                        }
                        binding.chipGroupCategory.addView(chip)
                    }
                }
            }
        }

        binding.btnTime.apply {
            addTextChangedListener {
                viewModel.addButtonTimeListener(it)
            }
            setOnClickListener {
                showClock()
            }
        }
        binding.btnDate.apply {
            addTextChangedListener {
                viewModel.addButtonDateListener(it)
            }
            setOnClickListener {
                showDate()
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

    private fun setValueFromViewModel() {
        binding.inputAmount.setText(viewModel.amount.value)
        binding.btnDate.text = viewModel.date.value
        binding.btnTime.text = viewModel.time.value
        accountAdapterStart.setPosition(viewModel.accountIdStart.value)
        accountAdapterEnd.setPosition(viewModel.accountIdDest.value)
    }

    private fun showDate() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
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
            requireContext(),
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
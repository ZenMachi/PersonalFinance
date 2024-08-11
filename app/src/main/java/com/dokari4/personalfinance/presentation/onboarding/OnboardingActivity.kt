package com.dokari4.personalfinance.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ActivityOnboardingBinding
import com.dokari4.core.domain.model.Account
import com.dokari4.core.domain.model.Category
import com.dokari4.core.domain.model.User
import com.dokari4.personalfinance.presentation.main.MainActivity
import com.dokari4.core.util.enums.AccountType
import com.dokari4.core.util.enums.CategoryType
import com.dokari4.core.util.enums.OnboardingState
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val viewModel: OnboardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            inputName.addTextChangedListener {
                viewModel.addEditTextNameListener(it)
            }


            fabDoneAccount.setOnClickListener {
                registerApp()
                lifecycleScope.launch {
                    viewModel.setOnboardingState(OnboardingState.DONE).await()
                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                    finish()
                }


            }
        }
        createChipsAccount()
        createChipsCategory()

        lifecycleScope.launch {
            viewModel.isValidCategory().collect {
                binding.fabNextCategory.isEnabled = viewModel.categoryChecked.value
                Log.d("BTN NEXT", "IsEnabled: $it")
                Log.d("Valid?", "categoryCheck: ${viewModel.categoryChecked.value}")
            }
        }

        lifecycleScope.launch {
            viewModel.isValidName().collect {
                binding.fabNextName.isEnabled = viewModel.name.value.isNotEmpty()
                Log.d("BTN NEXT", "IsEnabled: $it")
                Log.d("Valid?", "categoryCheck: ${viewModel.categoryChecked.value}")
            }
        }
    }

    private fun registerApp() {
        lifecycleScope.launch {
            val user = User(
                name = viewModel.name.value,
            )

            val categories = binding.chipGroupCategory.checkedChipIds
                .map { binding.chipGroupCategory.findViewById<Chip>(it).text.toString() }

            val accounts = binding.chipGroupAccount.checkedChipIds
                .map { binding.chipGroupAccount.findViewById<Chip>(it).text.toString() }

            viewModel.insertUser(user).await()

            if (categories.isNotEmpty()) {
                categories.forEach {
                    viewModel.insertCategory(Category(name = it)).await()
                }
            }
            if (accounts.isNotEmpty()) {
                accounts.forEach {
                    val account = Account(
                        userId = 1,
                        accountType = it,
                        name = viewModel.name.value,
                        amount = 0.0
                    )
                    viewModel.insertAccount(account).await()
                }
            }
        }
    }

    private fun createChipsAccount() {
        val categoryDummy = listOf(
            AccountType.BANK.toString(),
            AccountType.CASH.toString(),
            AccountType.E_WALLET.toString()
        )
        categoryDummy.forEach { account ->
            val chip = layoutInflater.inflate(
                R.layout.item_chip_category,
                binding.chipGroupCategory,
                false
            ) as Chip
            chip.apply {
                text = account
                chipIcon = when (AccountType.fromDescription(account)) {
                    AccountType.BANK -> AppCompatResources.getDrawable(context, R.drawable.ic_bank_24)
                    AccountType.CASH -> AppCompatResources.getDrawable(context, R.drawable.ic_cash_24)
                    AccountType.E_WALLET -> AppCompatResources.getDrawable(context, R.drawable.ic_credit_card_24)
                }
            }
            binding.chipGroupAccount.addView(chip)
        }

//        binding.chipGroupAccount.apply {
//            setOnCheckedStateChangeListener { _, checkedIds ->
//
//                for (checkedId in checkedIds) {
//                    val chip: Chip = findViewById(checkedId)
//                    val selectedAccount = chip.text.toString()
//                    viewModel.addAccount(selectedAccount)
//                }
//
////                if (checkedIds.isNotEmpty()) {
////                    val chip: Chip = findViewById(checkedIds.first())
////                    val selectionId = chip.id
////                    viewModel.updateCategory(selectionId)
////                } else {
////                    viewModel.updateCategory(null)
////                }
//            }
//        }
    }

    private fun createChipsCategory() {
        val categoryDummy = listOf(
            CategoryType.FOOD.toString(),
            CategoryType.SHOPPING.toString(),
            CategoryType.SUBSCRIPTION.toString(),
            CategoryType.TRANSPORTATION.toString(),
            CategoryType.HEALTH.toString(),
            CategoryType.EDUCATION.toString(),
            CategoryType.GIFTS.toString(),
            CategoryType.TRANSFER.toString()
        )
        categoryDummy.forEach { category ->
            val chip = layoutInflater.inflate(
                R.layout.item_chip_category,
                binding.chipGroupCategory,
                false
            ) as Chip
            chip.apply {
                text = category
                chipIcon = when (CategoryType.fromDescription(category)) {
                    CategoryType.FOOD -> AppCompatResources.getDrawable(context, R.drawable.ic_food_24)
                    CategoryType.SHOPPING -> AppCompatResources.getDrawable(context, R.drawable.ic_shopping_24)
                    CategoryType.SUBSCRIPTION -> AppCompatResources.getDrawable(context, R.drawable.ic_subscription_24)
                    CategoryType.TRANSPORTATION -> AppCompatResources.getDrawable(context, R.drawable.ic_transportation_24)
                    CategoryType.HEALTH -> AppCompatResources.getDrawable(context, R.drawable.ic_health_24)
                    CategoryType.EDUCATION -> AppCompatResources.getDrawable(context, R.drawable.ic_education_24)
                    CategoryType.GIFTS -> AppCompatResources.getDrawable(context, R.drawable.ic_gift_24)
                    CategoryType.TRANSFER -> AppCompatResources.getDrawable(context, R.drawable.ic_transfer_24)
                }
                if (category == CategoryType.TRANSFER.toString()) {
                    isChecked = true
                    isCheckable = false
                }
            }
            binding.chipGroupCategory.addView(chip)
        }

        binding.chipGroupCategory.apply {
            setOnCheckedStateChangeListener { _, checkedIds ->
//                viewModel.setValidCategory(checkedIds.isNotEmpty())
                if (checkedIds.size > 1) {
                    viewModel.setValidCategory(true)
                    Log.d("TAG", "setValidCategory: ${viewModel.categoryChecked.value}")
                } else {
                    viewModel.setValidCategory(false)
                    Log.d("TAG", "setValidCategory: ${viewModel.categoryChecked.value}")
                }
            }
        }
    }
}
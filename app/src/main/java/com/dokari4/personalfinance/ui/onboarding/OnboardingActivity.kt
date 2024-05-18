package com.dokari4.personalfinance.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.ActivityOnboardingBinding
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.ui.main.MainActivity
import com.dokari4.personalfinance.util.OnboardingState
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
            fabDone.setOnClickListener {
                setUpUser()
                lifecycleScope.launch {
                    viewModel.setOnboardingState(OnboardingState.DONE).await()
                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                    finish()
                }


            }
        }
    }

    private fun setUpUser() {

        lifecycleScope.launch {
            val user = User(
                name = viewModel.name.value,
            )
            val categoryDummy = listOf(
                Category(name = "Food"),
                Category(name = "Shopping"),
                Category(name = "Subscription"),
                Category(name = "Transportation"),
                Category(name = "Health"),
                Category(name = "Education"),
                Category(name = "Gifts"),
            )
            viewModel.insertUser(user).await()
            for (category in categoryDummy) {
                viewModel.insertCategory(category).await()
            }
        }


    }
}
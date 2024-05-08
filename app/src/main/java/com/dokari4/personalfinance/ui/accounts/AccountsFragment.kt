package com.dokari4.personalfinance.ui.accounts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.ui.add_account.AddAccountActivity
import com.dokari4.personalfinance.databinding.FragmentAccountsBinding
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private lateinit var accountAdapter: AccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            accountAdapter = AccountAdapter()
            val userDummy = User(name = "Sharon Sharp")
            val dataDummy = Account(
                userId = 0,
                accountType = "natoque",
                name = "Santiago O'Neill",
                amount = 2.3
            )
            val categoryDummy = listOf(
                Category(name = "Food"),
                Category(name = "Shopping"),
                Category(name = "Entertainment"),
                Category(name = "Transportation"),
                Category(name = "Health"),
                Category(name = "Education"),
                Category(name = "Other"),
            )

            binding.rvAccounts.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = accountAdapter
            }

//            viewModel.getAccounts.observe(viewLifecycleOwner) { account ->
//                accountAdapter.submitList(account)
//                Log.d("AccountFragment", "onViewCreated: $account")
//            }

            viewModel.accountsWithTransactions.observe(viewLifecycleOwner) { account ->
                accountAdapter.submitList(account)
                Log.d("AccountFragment", "onViewCreated: $account")
            }



            binding.fabAdd.setOnClickListener {
                val intent = Intent(context, AddAccountActivity::class.java)
                startActivity(intent)
            }
            binding.fabAddUser.setOnClickListener {
                viewModel.insertUser(userDummy)
                for (category in categoryDummy) {
                 viewModel.insertCategory(category)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
package com.dokari4.personalfinance.ui.accounts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.data.State
import com.dokari4.personalfinance.ui.add_account.AddAccountActivity
import com.dokari4.personalfinance.databinding.FragmentAccountsBinding
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.math.BigDecimal

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private val accountAdapter: AccountAdapter by lazy { AccountAdapter() }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

        Log.d("AccountFragment", "Lifecycle: ${lifecycle.currentState.name}")

        lifecycleScope.launch {
            viewModel.isValid().collect {
                binding.tvNoAccounts.visibility = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountsWithTransactions.collect { accounts ->
                    accountAdapter.submitList(accounts)
                    Log.d("AccountFragment", "onViewCreated: $accounts")
                }
            }

        }


//            viewModel.accountsWithTransactions.observe(viewLifecycleOwner) { accounts ->
//                accountAdapter.submitList(accounts)
//                Log.d("AccountFragment", "onViewCreated: $accounts")
//            }


        binding.fabAdd.setOnClickListener {
            val intent = Intent(context, AddAccountActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()
        Log.d("AccountFragment", "Lifecycle OnStart: ${lifecycle.currentState.name}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("AccountFragment", "Lifecycle OnResume: ${lifecycle.currentState.name}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("AccountFragment", "Lifecycle OnPause: ${lifecycle.currentState.name}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("AccountFragment", "Lifecycle OnStop: ${lifecycle.currentState.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AccountFragment", "Lifecycle OnDestroy: ${lifecycle.currentState.name}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("AccountFragment", "Lifecycle OnDetach: ${lifecycle.currentState.name}")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("AccountFragment", "Lifecycle OnViewStateRestored: ${lifecycle.currentState.name}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("AccountFragment", "Lifecycle OnSaveInstanceState: ${lifecycle.currentState.name}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("AccountFragment", "Lifecycle OnActivityCreated: ${lifecycle.currentState.name}")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d("AccountFragment", "Lifecycle OnHiddenChanged: ${lifecycle.currentState.name}")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("AccountFragment", "Lifecycle : ${lifecycle.currentState.name}")
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        Log.d("AccountFragment", "Lifecycle OnAttach: ${lifecycle.currentState.name}")
    }

}
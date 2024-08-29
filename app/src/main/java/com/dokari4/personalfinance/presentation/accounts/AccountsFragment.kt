package com.dokari4.personalfinance.presentation.accounts

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
import com.dokari4.personalfinance.databinding.FragmentAccountsBinding
import com.dokari4.personalfinance.presentation.add_account.AddAccountActivity
import com.dokari4.personalfinance.presentation.update_account.UpdateAccountActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        Log.d("AccountFragment", "Lifecycle: ${lifecycle.currentState.name}")

        binding.rvAccounts.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = accountAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    accountAdapter.submitList(state.accounts)
                    binding.tvNoAccounts.visibility = if (state.isEmpty == true) View.VISIBLE else View.GONE
                    Log.d("AccountFragment", "onViewCreated: $state")
                }
            }

        }

        accountAdapter.onItemClick = {
            val intent = Intent(context, UpdateAccountActivity::class.java)
            intent.putExtra(UpdateAccountActivity.ACCOUNT_WITH_TRANSACTIONS, it.data)
            Log.d("AccountFragment", "AccountAdapter: $it")
            startActivity(intent)
        }

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
package com.dokari4.personalfinance.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.databinding.FragmentAccountsBinding
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.ui.AccountAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val accountAdapter = AccountAdapter()
            val dataDummy = Account(
                id = 0,
                userId = 2252,
                accountType = "natoque",
                name = "Santiago O'Neill",
                amount = 2.3
            )

            viewModel.getAccounts.observe(viewLifecycleOwner, { account ->
                accountAdapter.setData(account)
            })

            with(binding.rvAccounts) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = accountAdapter
            }

            binding.fabAdd.setOnClickListener {
                viewModel.insertAccount(dataDummy)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
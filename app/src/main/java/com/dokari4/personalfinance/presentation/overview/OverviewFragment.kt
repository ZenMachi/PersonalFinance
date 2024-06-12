package com.dokari4.personalfinance.presentation.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dokari4.personalfinance.databinding.FragmentOverviewBinding
import com.dokari4.personalfinance.presentation.overview.fragments.CategoryFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OverviewViewModel by viewModels()


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(
            CategoryFragment.newInstance().apply {
                arguments = Bundle().apply {
                    putInt("position", 0)
                }
            },
            CategoryFragment.newInstance().apply {
                arguments = Bundle().apply {
                    putInt("position", 1)
                }
            },
        )
        val titleFragments = listOf(
            "Income",
            "Expense"
        )
        val viewPagerAdapter = ViewPagerAdapter(requireActivity(), fragments)

        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleFragments[position]
        }.attach()

//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when (tab?.position) {
//                    0 -> {
//                        viewModel.getOverviewTypeIncome(viewLifecycleOwner)
//                        Log.d("OverviewFragment", "onTabSelected: Income")
//                        viewModel.typeIncome.observe(viewLifecycleOwner) {
//                            Log.d("OverviewFragment", "onTabSelected: $it")
//                        }
//                    }
//
//                    1 -> {
//                        viewModel.getOverviewTypeExpense(viewLifecycleOwner)
//                        Log.d("OverviewFragment", "onTabSelected: Expense")
//                        viewModel.typeExpense.observe(viewLifecycleOwner) {
//                            Log.d("OverviewFragment", "onTabSelected: $it")
//                        }
//                    }
//                }
//            }
//
//            override fun onTabUnselected(p0: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(p0: TabLayout.Tab?) {
//
//            }
//
//        })


    }
}
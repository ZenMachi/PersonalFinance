package com.dokari4.personalfinance.ui.overview.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.FragmentCategoryBinding
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.ui.overview.OverviewViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val lazyColors: List<Int> by lazy {
        return@lazy listOf(
            requireContext().getColor(R.color.md_theme_primary),
            requireContext().getColor(R.color.md_theme_primaryContainer),
            requireContext().getColor(R.color.md_theme_secondary),
            requireContext().getColor(R.color.md_theme_secondaryContainer),
            requireContext().getColor(R.color.md_theme_tertiary),
            requireContext().getColor(R.color.md_theme_tertiaryContainer),
            requireContext().getColor(R.color.md_theme_error),
        )
    }

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OverviewViewModel by viewModels()
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(lazyColors)
    }

    private var type: Int by Delegates.notNull()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getInt("position")!!
        when (type) {
            0 -> viewModel.getCategoryTotalIncome.observe(viewLifecycleOwner) {
                Log.d("CategoryFragment", "onViewCreated: $it")
                initPieChart(it, lazyColors)
                initRecyclerView(it)
            }
            1 -> viewModel.getCategoryTotalExpense.observe(viewLifecycleOwner) {
                Log.d("CategoryFragment", "onViewCreated: $it")
                initPieChart(it, lazyColors)
                initRecyclerView(it)
            }
        }
        Log.d("CategoryFragment", "onViewCreated: $type")






        binding.rvCategories.apply {
            adapter = categoryAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initRecyclerView(data: List<CategoryCountTotal>) {
        val list = data.filter { it.count > 0 }
        categoryAdapter.submitList(list)
    }

    private fun initPieChart(data: List<CategoryCountTotal>, colors: List<Int>) {
        val pieEntry = data
            .filter { it.count > 0 }
            .map {
                PieEntry(it.count.toFloat(), it.name)
            }

        val pieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.sliceSpace = 5f
        pieDataSet.colors = colors

        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
        pieData.setValueTextSize(16f)
        pieData.setValueTextColor(requireContext().getColor(R.color.md_theme_background))

        binding.pieChart.data = pieData

        with(binding) {
            pieChart.holeRadius = 70f
            pieChart.setTransparentCircleAlpha(0)
            pieChart.setUsePercentValues(true)
            pieChart.setHoleColor(requireContext().getColor(R.color.md_theme_surfaceContainerLow))
            pieChart.animateY(1000, Easing.EaseInOutQuad)
            pieChart.description.isEnabled = false
            pieChart.legend.isEnabled = false

            pieChart.invalidate()
        }

    }

    companion object {
        fun newInstance() = CategoryFragment()
    }


}
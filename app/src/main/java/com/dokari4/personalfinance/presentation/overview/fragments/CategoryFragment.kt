package com.dokari4.personalfinance.presentation.overview.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.FragmentCategoryBinding
import com.dokari4.core.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.presentation.overview.OverviewViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val lazyColors: List<Int> by lazy {
        return@lazy listOf(
            requireContext().getColor(R.color.colorIconColor1Container),
            requireContext().getColor(R.color.colorIconColor2Container),
            requireContext().getColor(R.color.colorIconColor3Container),
            requireContext().getColor(R.color.colorIconColor4Container),
            requireContext().getColor(R.color.colorIconColor5Container),
            requireContext().getColor(R.color.colorIconColor6Container),
            requireContext().getColor(R.color.colorIconColor7Container),
        )
    }

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OverviewViewModel by viewModels()
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(lazyColors)
    }

    private var position: Int by Delegates.notNull()

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
        position = arguments?.getInt("position")!!
        when (position) {
            0 -> viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getCategoryTotalIncome.collect { listData ->
                        val data = listData.filter { it.count > 0 }

                        viewModel.isContentEmpty(data).collect {
                            binding.tvNoData.visibility = it
                        }

                        Log.d("CategoryFragment", "onViewCreated: $listData")
                        Log.d("CategoryFragment", "Data: $data")
                        initPieChart(listData, lazyColors)
                        initRecyclerView(listData)
                    }
                }
            }

            1 -> viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getCategoryTotalExpense.collect { listData ->
                        val data = listData.filter { it.count > 0 }

                        viewModel.isContentEmpty(data).collect {
                            binding.tvNoData.visibility = it
                        }

                        Log.d("CategoryFragment", "onViewCreated: $listData")
                        Log.d("CategoryFragment", "Data: $data")
                        initPieChart(listData, lazyColors)
                        initRecyclerView(listData)
                    }
                }
            }
        }
        Log.d("CategoryFragment", "Position: $position")

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

        val textColors = listOf(
            requireContext().getColor(R.color.colorOnIconColor1Container),
            requireContext().getColor(R.color.colorOnIconColor2Container),
            requireContext().getColor(R.color.colorOnIconColor3Container),
            requireContext().getColor(R.color.colorOnIconColor4Container),
            requireContext().getColor(R.color.colorOnIconColor5Container),
            requireContext().getColor(R.color.colorOnIconColor6Container),
            requireContext().getColor(R.color.colorOnIconColor7Container),
        )

        val pieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.sliceSpace = 5f
        pieDataSet.colors = colors

        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
        pieData.setValueTextSize(18f)
        pieData.setValueTextColors(textColors)

        binding.pieChart.data = pieData

        with(binding) {
            pieChart.holeRadius = 70f
            pieChart.setTransparentCircleAlpha(0)
            pieChart.setUsePercentValues(true)
            pieChart.setHoleColor(requireContext().getColor(R.color.md_theme_primary))
            binding.pieChart.setEntryLabelColor(requireContext().getColor(R.color.md_theme_onPrimaryContainer))
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
package com.dokari4.personalfinance.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dokari4.personalfinance.R
import com.dokari4.personalfinance.databinding.FragmentOverviewBinding
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private lateinit var binding: FragmentOverviewBinding
    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCategoryTotalTransaction.observe(viewLifecycleOwner) {
            initPieChart(it)
        }
    }

    private fun initPieChart(data: List<CategoryCountTotal>) {
        val pieEntry = data
            .filter { it.count > 0 }
            .map {
                PieEntry(it.count.toFloat(), it.name)
            }

//        val colors = listOf(
//            ColorTemplate.colorWithAlpha(R.color.md_theme_primary, 1),
//            ColorTemplate.colorWithAlpha(R.color.md_theme_secondary, 1),
//            ColorTemplate.colorWithAlpha(R.color.md_theme_tertiary, 1),
//            ColorTemplate.colorWithAlpha(R.color.md_theme_error, 1),
//        )

        val pieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.sliceSpace = 3f
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(PercentFormatter(binding.pieChart))
        pieData.setValueTextSize(24f)
        pieData.setValueTextColor(R.color.md_theme_background)

        binding.pieChart.data = pieData

        with(binding) {
            pieChart.setUsePercentValues(true)
            pieChart.animateY(1000, Easing.EaseInOutQuad)
            pieChart.description.isEnabled = false

            pieChart.invalidate()
        }

    }

}
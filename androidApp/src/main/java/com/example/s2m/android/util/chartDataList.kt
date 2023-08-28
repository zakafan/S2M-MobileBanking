package com.example.s2m.android.util

import com.himanshoe.charty.common.ChartData

data class ConcreteChartData(
    override val xValue: Any,
    override val yValue: Float,
    override val chartString: String
) : ChartData

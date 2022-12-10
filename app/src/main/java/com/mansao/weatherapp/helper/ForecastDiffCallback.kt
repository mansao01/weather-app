package com.mansao.weatherapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.mansao.weatherapp.data.network.response.HourItem

class ForecastDiffCallback(
    private val mOldForecastList: List<HourItem>,
    private val mNewForecastList: List<HourItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = mOldForecastList.size

    override fun getNewListSize() = mNewForecastList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldForecastList[oldItemPosition].time == mNewForecastList[newItemPosition].time
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldForecastList[oldItemPosition]
        val newEmployee = mNewForecastList[newItemPosition]
        return oldEmployee.condition ==newEmployee.condition
    }
}
package com.kaisho.carforrent.room.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.core.util.Pair
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.kaisho.carforrent.model.FavoritePOJO
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class SharedViewModel(application: Application): AndroidViewModel(application){
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)
    val fromLiveData : MutableLiveData<String> = MutableLiveData()


    @SuppressLint("SimpleDateFormat")
    var dateFrom: String = "${SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time)} Выберите дату"

    fun checkIfDatabaseEmpty(favoriteList: List<FavoritePOJO>){
        emptyDatabase.value = favoriteList.isEmpty()
    }

    fun datePicker(): MaterialDatePicker.Builder<Pair<Long,Long>>{
        //MaterialDatePicker
        val constraintBuilder = CalendarConstraints.Builder()
        constraintBuilder.setValidator(DateValidatorPointForward.now())


        val builder : MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("SELECT A DATE")
        builder.setCalendarConstraints(constraintBuilder.build())

        return builder
    }

    fun calculate(model: List<String>){
        val s1 = Date(model[0].substring(5, model[0].length).toLong())
        val s2 = Date(model[1].substring(0, model[1].length -1).toLong())

        val calendarFrom = Calendar.getInstance()
        val calendarUntil = Calendar.getInstance()
        calendarFrom.time = s1
        calendarUntil.time = s2

        var count = 0

        while (calendarFrom.compareTo(calendarUntil) != 0){
            count++
            calendarFrom.add(Calendar.DATE, 1)
        }
        Log.d("MyLog", count.toString())
    }

    fun datePick(model: List<String>){
        val sdf = SimpleDateFormat("dd.mm.yyyy")
        val text = "${sdf.format(model[0].substring(5, model[0].length).toLong()).toString()} " +
                sdf.format(model[1].substring(0, model[1].length -1).toLong()).toString()
        fromLiveData.value = text
    }

    fun getDateFrom(): MutableLiveData<String>{
        fromLiveData.value = dateFrom
        return fromLiveData
    }
}
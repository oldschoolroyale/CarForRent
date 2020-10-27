package com.kaisho.carforrent.viewModel

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import androidx.core.util.Pair
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class ListViewModel : ViewModel() {
    @SuppressLint("SimpleDateFormat")
    private var dateFrom: String = "${SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time)} Choose date"

    private val fromLiveData : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val daysList: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    var daysInt = 0


    fun datePicker(): MaterialDatePicker.Builder<Pair<Long, Long>>{
        //MaterialDatePicker
        val constraintBuilder = CalendarConstraints.Builder()
        constraintBuilder.setValidator(DateValidatorPointForward.now())

        val builder : MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("SELECT A DATE")
        builder.setCalendarConstraints(constraintBuilder.build())

        return builder
    }

    fun calculate(model: List<String>) : Flowable<Int> {
        return Flowable.create({ subscriber ->
            var count = 0
            val s1 = Date(model[0].substring(5, model[0].length).toLong())
            val s2 = Date(model[1].substring(0, model[1].length -1).toLong())

            val calendarFrom = Calendar.getInstance()
            val calendarUntil = Calendar.getInstance()
            calendarFrom.time = s1
            calendarUntil.time = s2

            while (calendarFrom.compareTo(calendarUntil) != 0){
                count++
                calendarFrom.add(Calendar.DATE, 1)
                subscriber.onNext(count)
            }

            daysInt = count
            subscriber.onComplete()
        }, BackpressureStrategy.BUFFER)

    }

    fun countDown(model: List<String>) : Observable<String>{

        val s1 = model[0].substring(5, model[0].length).toLong()
        val s2 = model[1].substring(0, model[1].length -1).toLong()
        Log.d("MyLog", "countdown $s1 $s2")
        return Observable.create{subscriber ->
            for (i in s2 downTo s1){
                Thread.sleep(1000)
                val df = Date(i * 1000)
                val date = SimpleDateFormat("HH/mm/ss").format(df)
                val model = date.toString().split("/")
                val response = "${model[0]} hours / ${model[1]} minutes / ${model[2]} seconds"
                subscriber.onNext(response)
            }
        }
    }

    fun setDateToList(){
        daysList.value = daysInt
    }

    fun datePick(model: List<String>){
        val sdf = SimpleDateFormat("dd.mm.yyyy")
        dateFrom = "${sdf.format(model[0].substring(5, model[0].length).toLong()).toString()} " +
                sdf.format(model[1].substring(0, model[1].length -1).toLong()).toString()
        fromLiveData.value = dateFrom
    }

    fun getDateFrom(): MutableLiveData<String> {
        fromLiveData.value = dateFrom
        return fromLiveData
    }

    fun getDaysInt(): MutableLiveData<Int>{
        daysList.value = daysInt
        return daysList
    }
}
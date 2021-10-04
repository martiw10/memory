package com.example.memory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule

class MainViewModel : ViewModel() {

    var seconds: MutableLiveData<Int> = MutableLiveData()
    var punt: MutableLiveData<Int> = MutableLiveData()
    var first : Int
    var second : Int
    lateinit var timer: TimerTask


    init {
        punt.value = 0
        seconds.value = 0
        timer = Timer().schedule(0, 1000) {
            seconds.postValue(seconds.value!! +1)
        }

        first = -1
        second = -1
    }

    var cards = listOf(
        R.drawable.bucks,
        R.drawable.bucks,
        R.drawable.cavs,
        R.drawable.cavs,
        R.drawable.celtics,
        R.drawable.celtics,
        R.drawable.heat,
        R.drawable.heat,
        R.drawable.hornets,
        R.drawable.hornets,
        R.drawable.mavs,
        R.drawable.mavs,
        R.drawable.nets,
        R.drawable.nets,
        R.drawable.warriors,
        R.drawable.warriors
    ).shuffled()

    fun match() : Boolean {
        if (cards[first] == cards[second]) return true
        return false
    }
}
package com.example.memory

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var  viewModel: MainViewModel
    private lateinit var buttons: List<ImageButton>
    private lateinit var bol: MutableList<Boolean>
    var final = true
    var puntf = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.seconds.observe(this, Observer { newInput ->
            textView.text = newInput.toString()
        })

        viewModel.punt.observe(this, Observer { newInput ->
            textView2.text = newInput.toString()
        })

        initButtons()
        hideAllCards()

        val ButtonListener = View.OnClickListener {
            val index = buttons.indexOf(it as ImageButton)
            it.setImageResource(viewModel.cards[index])
            if (!(bol[index])) comp(index)
        }
        buttons.forEach { it.setOnClickListener(ButtonListener) }
    }

    private fun initButtons() {
        buttons = listOf(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16)
        bol = mutableListOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    }

    private fun hideAllCards() {
        buttons.forEach {
            if(!bol[buttons.indexOf(it)]) {
                final = false
                it.setImageResource(R.drawable.nba)
            }
        }
        if (final) {
            puntf = false
            viewModel.timer.cancel()
                val toast = Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT)
            toast.show()
        }
        final = true
    }

    private fun comp(index : Int) {
        if (viewModel.first == -1) {
            if (puntf) viewModel.punt.postValue(viewModel.punt.value!! +1)
            viewModel.first = index
        }
        else if (viewModel.second == -1 && (index != viewModel.first)) {
            if (puntf) viewModel.punt.postValue(viewModel.punt.value!! +1)
            viewModel.second = index
            if (viewModel.match()) {
                bol[viewModel.first] = true
                bol[viewModel.second] = true
            }
            Handler().postDelayed({
                hideAllCards()
            }, 500)
            viewModel.first = -1
            viewModel.second = -1
        }
    }
}

package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    private lateinit var billAmount: EditText //late initialization
    private lateinit var seekBarSlide: SeekBar
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView
    private lateinit var tipPercentage: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        billAmount = findViewById(R.id.billAmount)
        seekBarSlide = findViewById(R.id.seekBarSlide)
        tipAmount = findViewById(R.id.tipAmount)
        totalAmount = findViewById(R.id.totalAmount)
        tipPercentage = findViewById(R.id.tipPercentage)

        seekBarSlide.progress = INITIAL_TIP_PERCENT
        tipPercentage.text = "$INITIAL_TIP_PERCENT%"

        seekBarSlide.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tipPercentage.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        billAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

        })

    }

    private fun computeTipAndTotal() {
        // 1. Get the value of the base and tip percent
        val base = billAmount.text.toString().toDouble()
        val tipPercent = seekBarSlide.progress
        println(base)
        println(tipPercent)
        // println(type(tipPercent))
        // 2. Compute the tip and total

        val tipAmt = base * tipPercent / 100
        val totalAmt = base + tipAmt

        println(tipAmt)
        println(totalAmt)

        // 3. Update the UI

        tipAmount.text = tipAmt.toString()
        totalAmount.text = totalAmt.toString()
    }
}
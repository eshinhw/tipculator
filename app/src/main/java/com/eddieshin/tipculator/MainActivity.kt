package com.eddieshin.tipculator

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    private lateinit var billAmount: EditText //late initialization

    private lateinit var seekBarSlide: SeekBar
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView
    private lateinit var tipPercentage: TextView

    private lateinit var tenButton: Button
    private lateinit var fifteenButton: Button
    private lateinit var twentyButton: Button
    private lateinit var thirtyButton: Button

    private lateinit var tipDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        billAmount = findViewById(R.id.etBillAmount)
        seekBarSlide = findViewById(R.id.sbTipPercentage)
        tipAmount = findViewById(R.id.tvTipAmount)
        totalAmount = findViewById(R.id.totalAmount)
        tipPercentage = findViewById(R.id.tvCurrTipPercentage)

        tenButton = findViewById(R.id.btnTenPct)
        fifteenButton = findViewById(R.id.btnFifteenPct)
        twentyButton = findViewById(R.id.btnTwentyPct)
        thirtyButton = findViewById(R.id.btnThirtyPct)

        tipDescription = findViewById(R.id.tvTipDescription)

        tenButton.setOnClickListener{
            seekBarSlide.progress = 10
            computeTipAndTotal()
        }

        fifteenButton.setOnClickListener{
            seekBarSlide.progress = 15
            computeTipAndTotal()
        }

        twentyButton.setOnClickListener{
            seekBarSlide.progress = 20
            computeTipAndTotal()

        }

        thirtyButton.setOnClickListener{
            seekBarSlide.progress = 30
            computeTipAndTotal()
        }

        seekBarSlide.progress = INITIAL_TIP_PERCENT
        tipPercentage.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)

        seekBarSlide.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tipPercentage.text = "$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
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

        if (billAmount.text.isEmpty()) {
            tipAmount.text = ""
            totalAmount.text = ""
            return
        }
        // 1. Get the value of the base and tip percent
        val base = billAmount.text.toString().toDouble()
        val tipPercent = seekBarSlide.progress
        println(base)
        println(tipPercent)

        // 2. Compute the tip and total
        val tipAmt = base * tipPercent / 100
        val totalAmt = base + tipAmt

        // 3. Update the UI
        tipAmount.text = "$ " + "%.2f".format(tipAmt)
        totalAmount.text = "$ " + "%.2f".format(totalAmt)

    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipWord = when (tipPercent) {
            in 0..5 -> "Horrible"
            in 6..10 -> "Poor"
            in 11..15 -> "Acceptable"
            in 16..20 -> "Great"
            in 21..25 -> "Excellent"
            else -> "Fantastic"
        }

        tipDescription.text = tipWord

        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat() / seekBarSlide.max,
            ContextCompat.getColor(this, R.color.worst),
            ContextCompat.getColor(this, R.color.best)
        ) as Int

        tipDescription.setTextColor(color)
    }
}
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
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBillAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

        })

        sbTipPercentage.progress = INITIAL_TIP_PERCENT
        tvTipPercentage.text = "$INITIAL_TIP_PERCENT%"
//        updateTipDescription(INITIAL_TIP_PERCENT)

        sbTipPercentage.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentage.text = "$progress%"
                computeTipAndTotal()
//                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        btnTenPct.setOnClickListener{
            sbTipPercentage.progress = 10
            computeTipAndTotal()
        }

        btnFifteenPct.setOnClickListener{
            sbTipPercentage.progress = 15
            computeTipAndTotal()
        }

        btnTwentyPct.setOnClickListener{
            sbTipPercentage.progress = 20
            computeTipAndTotal()

        }

        btnThirtyPct.setOnClickListener{
            sbTipPercentage.progress = 30
            computeTipAndTotal()
        }
    }

    private fun computeTipAndTotal() {


        if (etBillAmount.text.isEmpty()) {
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        // 1. Get the value of the base and tip percent
        val base = etBillAmount.text.toString().toDouble()
        val tipPercent = sbTipPercentage.progress
        println(base)
        println(tipPercent)

        // 2. Compute the tip and total
        val tipAmt = base * tipPercent / 100
        val totalAmt = base + tipAmt

        // 3. Update the UI
        tvTipAmount.text = "$ " + "%.2f".format(tipAmt)
        tvTotalAmount.text = "$ " + "%.2f".format(totalAmt)

    }

//    private fun updateTipDescription(tipPercent: Int) {
//        val tipWord = when (tipPercent) {
//            in 0..5 -> "Horrible"
//            in 6..10 -> "Poor"
//            in 11..15 -> "Acceptable"
//            in 16..20 -> "Great"
//            in 21..25 -> "Excellent"
//            else -> "Fantastic"
//        }
//
//        tipDescription.text = tipWord
//
//        val color = ArgbEvaluator().evaluate(
//            tipPercent.toFloat() / seekBarSlide.max,
//            ContextCompat.getColor(this, R.color.worst),
//            ContextCompat.getColor(this, R.color.best)
//        ) as Int
//
//        tipDescription.setTextColor(color)
//    }
}
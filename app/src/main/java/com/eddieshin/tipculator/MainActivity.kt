package com.eddieshin.tipculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        setContentView(R.layout.activity_main)

        sbTipPercentage.progress = INITIAL_TIP_PERCENT
        tvTipPercentage.text = "$INITIAL_TIP_PERCENT%"

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

        etCustomTip.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                updateCustomTip()
                computeTipAndTotal()
            }

        })



        sbTipPercentage.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentage.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        ivMinus.setOnClickListener {
            val splitNum = tvSplitNum.text.toString().toInt()

            if (splitNum > 1) {
                tvSplitNum.text = "${splitNum-1}"
            }
            computeTipAndTotal()
        }

        ivPlus.setOnClickListener {
            val splitNum = tvSplitNum.text.toString().toInt()
            tvSplitNum.text = "${splitNum+1}"
            computeTipAndTotal()
        }

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

        btnCustomTip.setOnClickListener{
            if (tvCustomTipLabel.visibility == View.VISIBLE) {
                tvCustomTipLabel.visibility = View.INVISIBLE
            } else {
                tvCustomTipLabel.visibility = View.VISIBLE
            }

            if (etCustomTip.visibility == View.VISIBLE) {
                etCustomTip.visibility = View.INVISIBLE
            } else {
                etCustomTip.visibility = View.VISIBLE
            }

        }
    }

    private fun computeTipAndTotal() {
        if (etBillAmount.text.isEmpty()) {
            tvSplitBill.text = ""
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            tvSplitTip.text = ""
            tvSplitTotal.text = ""
            return
        }

        // 1. Get the value of the base and tip percent
        val base = etBillAmount.text.toString().toDouble()
        val tipPercent = tvTipPercentage.text.toString().dropLast(1).toInt()

        val tipAmt = base * tipPercent / 100
        val totalAmt = base + tipAmt

        if (tvSplitNum.text.isEmpty()) {
            tvTipAmount.text = "$ " + "%.2f".format(tipAmt)
            tvTotalAmount.text = "$ " + "%.2f".format(totalAmt)
            tvSplitBill.text = ""
            tvSplitTip.text = ""
            tvSplitTotal.text = ""
        } else {
            val splitNum = tvSplitNum.text.toString().toInt()

            if (splitNum > 1) {
                val splitBill = base / splitNum
                val splitTipAmt = tipAmt / splitNum
                val splitTotalAmt = totalAmt / splitNum

                tvTipAmount.text = "$ " + "%.2f".format(tipAmt)
                tvTotalAmount.text = "$ " + "%.2f".format(totalAmt)
                tvSplitBill.text = "$ " + "%.2f".format(splitBill)
                tvSplitTip.text = "$ " + "%.2f".format(splitTipAmt)
                tvSplitTotal.text = "$ " + "%.2f".format(splitTotalAmt)
            } else {

                tvTipAmount.text = "$ " + "%.2f".format(tipAmt)
                tvTotalAmount.text = "$ " + "%.2f".format(totalAmt)
                tvSplitBill.text = "$ " + "%.2f".format(base)
                tvSplitTip.text = "$ " + "%.2f".format(tipAmt)
                tvSplitTotal.text = "$ " + "%.2f".format(totalAmt)
            }
        }
    }

    private fun updateCustomTip() {

        if (etCustomTip.text.isEmpty()) {
            tvTipPercentage.text = ""
            sbTipPercentage.progress = 0
        } else {
            val customTipPct = etCustomTip.text.toString().toInt()
            Log.i(TAG, "$customTipPct")
            if (customTipPct <= 30) {
                Log.i(TAG, "second")
                tvTipPercentage.text = "$customTipPct%"
                sbTipPercentage.progress = customTipPct
            } else {
                Log.i(TAG, "last")
                sbTipPercentage.progress = 30
                tvTipPercentage.text = "$customTipPct%"
            }
        }
    }

}
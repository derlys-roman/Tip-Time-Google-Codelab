package com.example.tiptime

import android.icu.text.NumberFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.tiptime.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener { calculateTip() }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateTip() {

        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipamount.text = ""
            return
        }
        val tipPercent = when (binding.radiobuttongrup.checkedRadioButtonId) {
            R.id.amazing20 -> 0.20
            R.id.good18 -> 0.18
            else -> 0.15
        }
        var tip = cost * tipPercent
        if (binding.roundUpTip.isChecked) {
            tip = ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipamount.text = getString(R.string.tip_amount, formattedTip)

    }
}
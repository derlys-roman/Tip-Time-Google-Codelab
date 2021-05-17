package com.example.tiptime

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        //set a function calculateTip() in the UI element button @+id/calculate
        binding.calculate.setOnClickListener { calculateTip() }
        //set a function calculateTip() in the UI element EditText @+id/costOfServiceEditText
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateTip() {

        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            //set empty string in textView @+id/tipamount
            binding.tipamount.text = ""
            return
        }

        /* set value of radio group in variable tipPercent
        *  */
        val tipPercent = when (binding.radiobuttongroup.checkedRadioButtonId) {
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

    /*
    The handleKeyEvent() is a private helper function that hides the onscreen
    keyboard if the keyCode input parameter is equal to KeyEvent.KEYCODE_ENTER
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
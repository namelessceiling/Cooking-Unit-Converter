package com.example.cookingunitconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.cookingunitconverter.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { convertUnit() }
        binding.amountEditTextField.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun getBaseUnit(): String {
        return when (binding.baseUnit.checkedRadioButtonId) {
            R.id.base_cup -> "cups"
            R.id.base_fluid_oz -> "fl oz"
            R.id.base_gallons -> "gallons"
            R.id.base_pints -> "pints"
            R.id.base_quarts -> "quarts"
            R.id.base_tablespoon -> "tbsp"
            else -> "tsp"
        }
    }

    private fun getNewUnit(): String {
        return when (binding.convertedUnit.checkedRadioButtonId) {
            R.id.converted_cup -> "cups"
            R.id.converted_fluid_oz -> "fl oz"
            R.id.converted_gallons -> "gallons"
            R.id.converted_pints -> "pints"
            R.id.converted_quarts -> "quarts"
            R.id.converted_tablespoon -> "tbsp"
            else -> "tsp"
        }
    }

    private fun convertUnit() {
        val editableText = binding.amountEditTextField.text.toString()
        val amount = editableText.toDoubleOrNull()
        if (amount == null || amount == 0.0) {
            binding.result.text = getString(R.string.result, NumberFormat.getNumberInstance().format(0.0))
            return
        }
        NumberFormat.getInstance()
        val baseUnit = getBaseUnit()
        val newUnit = getNewUnit()
        if (baseUnit == newUnit) {
            binding.result.text = getString(R.string.result, NumberFormat.getNumberInstance().format(amount))
            return
        }
        val toCup = convertToCups(amount, baseUnit)
        var result = convertCupsToOthers(toCup, newUnit)
        if (binding.roundUpSwitch.isChecked) { result = kotlin.math.ceil(result) }
        binding.result.text = getString(R.string.result, NumberFormat.getNumberInstance().format(result))
    }

    private fun convertCupsToOthers(amount: Double, unitType: String): Double {
        return when (unitType) {
            "cups" -> amount
            "fl oz" -> amount * 8
            "gallons" -> amount / 16.0
            "pints" -> amount * 0.5
            "quarts" -> amount * 0.25
            "tbsp" -> amount * 16
            else -> amount * 48
        }
    }

    private fun convertToCups(amount: Double, unitType: String): Double {
        return when (unitType) {
            "cups" -> amount
            "fl oz" -> amount * 0.125
            "gallons" -> amount * 16
            "pints" -> amount * 2
            "quarts" -> amount * 4
            "tbsp" -> amount * 0.0625
            else -> amount * (1.0/48.0)
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
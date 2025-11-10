package com.example.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var currencyInput: TextView
    private lateinit var spinnerInput: Spinner
    private lateinit var spinnerOutput: Spinner
    private lateinit var currencyLabel: TextView
    private lateinit var gridLayout: GridLayout
    private val currencies = arrayOf("USD", "EUR", "JPY", "VND", "GBP", "AUD", "CAD", "CHF", "CNY", "KRW")
    private var isFirstSelection = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        currencyInput = findViewById(R.id.currency_input)
        spinnerInput = findViewById(R.id.spinner1)
        spinnerOutput = findViewById(R.id.spinner2)
        currencyLabel = findViewById(R.id.currency_label)
        gridLayout = findViewById(R.id.gridLayout)

        val adapter = ArrayAdapter(this, R.layout.spinner_item, currencies)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerInput.adapter = adapter
        spinnerOutput.adapter = adapter

        spinnerInput.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (isFirstSelection) {
                    isFirstSelection = false
                    return
                }
                updateConversion()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }

        spinnerOutput.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                updateConversion()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                handleButtonClick(button.text.toString())
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun handleButtonClick(value: String) {
        when (value) {
            "C" -> {
                currencyInput.text = "0"
                currencyLabel.text = "0"
            }
            "CE" -> {
                val text = currencyInput.text.toString()
                if (text.isNotEmpty() && text != "0") {
                    val newText = text.dropLast(1)
                    currencyInput.text = if (newText.isEmpty()) "0" else newText
                    updateConversion()
                }
            }
            " " -> {}
            else -> {
                if (currencyInput.text.toString() == "0") {
                    currencyInput.text = value
                }
                else {
                    currencyInput.text = "${currencyInput.text}$value"
                }
                updateConversion()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateConversion() {
        val input = currencyInput.text.toString()
        if (input.isNotEmpty() && input != "0") {
            val from = spinnerInput.selectedItem?.toString() ?: ""
            val to = spinnerOutput.selectedItem?.toString() ?: ""
            val converted = convertCurrency(input.toDoubleOrNull() ?: 0.0, from, to)
            currencyLabel.text = "$converted $to"
        }
        else {
            currencyLabel.text = "0"
        }
    }

    @SuppressLint("DefaultLocale")
    private fun convertCurrency(amount: Double, from: String, to: String): Double {
        val exchangeRates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.85,
            "JPY" to 110.0,
            "VND" to 23000.0,
            "GBP" to 0.73,
            "AUD" to 1.35,
            "CAD" to 1.25,
            "CHF" to 0.92,
            "CNY" to 6.45,
            "KRW" to 1180.0
        )

        val fromRate = exchangeRates[from] ?: 1.0
        val toRate = exchangeRates[to] ?: 1.0

        val result = (amount / fromRate) * toRate

        return String.format("%.2f", result).toDouble()
    }
}
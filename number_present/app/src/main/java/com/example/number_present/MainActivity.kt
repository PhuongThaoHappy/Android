package com.example.number_present

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var edtNumber: EditText
    private lateinit var listView: ListView
    private lateinit var rbOdd: RadioButton
    private lateinit var rbEven: RadioButton
    private lateinit var rbPrime: RadioButton
    private lateinit var rbPerfect: RadioButton
    private lateinit var rbSquare: RadioButton
    private lateinit var rbFibo: RadioButton
    private lateinit var buttons: List<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        edtNumber = findViewById(R.id.edtNumber)
        listView = findViewById(R.id.listView)

        rbOdd = findViewById(R.id.rbOdd)
        rbEven = findViewById(R.id.rbEven)
        rbPrime = findViewById(R.id.rbPrime)
        rbPerfect = findViewById(R.id.rbPerfect)
        rbSquare = findViewById(R.id.rbSquare)
        rbFibo = findViewById(R.id.rbFibo)

        buttons = listOf(rbOdd, rbEven, rbPrime, rbPerfect, rbSquare, rbFibo)

        buttons.forEach { btn ->
            btn.setOnClickListener {
                buttons.forEach { it.isChecked = false }
                btn.isChecked = true
                updateList()
            }
        }

        edtNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateList()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun updateList() {
        val limit = edtNumber.text.toString().toIntOrNull() ?: return
        if (limit <= 0) return

        val result = when {
            rbOdd.isChecked -> (1..limit).filter { it % 2 != 0 }
            rbEven.isChecked -> (1..limit).filter { it % 2 == 0 }
            rbPrime.isChecked -> (1..limit).filter { isPrime(it) }
            rbPerfect.isChecked -> (1..limit).filter { isPerfect(it) }
            rbSquare.isChecked -> (1..limit).filter { isSquare(it) }
            rbFibo.isChecked -> fibonacciUpTo(limit)
            else -> emptyList()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, result)
        listView.adapter = adapter
    }
    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }
    private fun isPerfect(n: Int): Boolean {
        if (n <= 1) return false
        val sum = (1 until n).filter { n % it == 0 }.sum()
        return sum == n
    }
    private fun isSquare(n: Int): Boolean {
        val root = sqrt(n.toDouble()).toInt()
        return root * root == n
    }
    private fun fibonacciUpTo(limit: Int): List<Int> {
        val list = mutableListOf(0, 1)
        while (true) {
            val next = list[list.size - 1] + list[list.size - 2]
            if (next > limit) break
            list.add(next)
        }
        return list.filter { it <= limit && it > 0 }
    }
}

package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var current: String = "0"
    private var previous: Double? = null
    private var pendingOp: Char? = null
    private var enteringNumber = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_layout)

        tvResult = findViewById(R.id.tvResult)

        // Các nút số
        val digits = listOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2", R.id.btn3 to "3",
            R.id.btn4 to "4", R.id.btn5 to "5", R.id.btn6 to "6", R.id.btn7 to "7",
            R.id.btn8 to "8", R.id.btn9 to "9"
        )
        digits.forEach { (id, num) ->
            findViewById<Button>(id).setOnClickListener { appendDigit(num) }
        }

        // Nút dấu chấm
        val btnDot = findViewById<Button>(R.id.btnDot)
        btnDot.setOnClickListener {
            if (!current.contains(".")) {
                current += "."
                enteringNumber = true
                updateDisplay()
            }
        }

        // Nút phép toán
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperator('+') }
        findViewById<Button>(R.id.btnSub).setOnClickListener { onOperator('-') }
        findViewById<Button>(R.id.btnMul).setOnClickListener { onOperator('*') }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { onOperator('/') }

        // Nút bằng
        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqual() }

        // Nút CE (xóa số hiện tại)
        findViewById<Button>(R.id.btnCE).setOnClickListener {
            current = "0"
            enteringNumber = false
            updateDisplay()
        }

        // Nút C (xóa toàn bộ)
        findViewById<Button>(R.id.btnC).setOnClickListener {
            current = "0"
            previous = null
            pendingOp = null
            enteringNumber = false
            updateDisplay()
        }

        // Nút backspace (xóa 1 ký tự)
        findViewById<Button>(R.id.btnBS).setOnClickListener {
            if (current.length > 1) {
                current = current.dropLast(1)
            } else {
                current = "0"
            }
            enteringNumber = current != "0"
            updateDisplay()
        }

        // Nút đổi dấu (+/-)
        findViewById<Button>(R.id.btnSign).setOnClickListener {
            current = if (current.startsWith("-")) current.drop(1)
            else "-$current"
            enteringNumber = true
            updateDisplay()
        }

        updateDisplay()
    }

    private fun appendDigit(digit: String) {
        if (!enteringNumber || current == "0") {
            current = digit
        } else {
            current += digit
        }
        enteringNumber = true
        updateDisplay()
    }

    private fun onOperator(op: Char) {
        val currVal = current.toDoubleOrNull() ?: return
        if (previous != null && pendingOp != null && enteringNumber) {
            previous = compute(previous!!, pendingOp!!, currVal)
        } else {
            previous = currVal
        }
        pendingOp = op
        enteringNumber = false
        current = "0"
        updateDisplay()
    }

    private fun onEqual() {
        val currVal = current.toDoubleOrNull() ?: return
        if (previous != null && pendingOp != null) {
            val result = compute(previous!!, pendingOp!!, currVal)
            current = result.toString()
            previous = null
            pendingOp = null
            enteringNumber = false
            updateDisplay()
        }
    }

    private fun compute(a: Double, op: Char, b: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b == 0.0) Double.NaN else a / b
            else -> b
        }
    }

    private fun updateDisplay() {
        tvResult.text = current
    }
}

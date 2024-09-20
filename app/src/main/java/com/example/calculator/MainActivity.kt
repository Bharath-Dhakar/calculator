package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var operator: String = ""
    var firstNumber: String = ""
    var secondNumber: String = ""
    var isOperatorSet: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvDisplay = findViewById<TextView>(R.id.tvDisplay)
        val result = findViewById<TextView>(R.id.result)
        val equals = findViewById<Button>(R.id.equal)
        val clear = findViewById<Button>(R.id.Clear)
        val AC = findViewById<Button>(R.id.AC)

        // Number buttons array
        val btns = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.Dot
        )

        // Operator buttons array
        val operators = arrayOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnDivide, R.id.btnMultiply, R.id.mod
        )

        // Clear button - resets everything
        AC.setOnClickListener {
            tvDisplay.text = "0"
            result.text = "0"
            firstNumber = ""
            secondNumber = ""
            operator = ""
            isOperatorSet = false
        }

        // Backspace (Clear last digit)
        clear.setOnClickListener {
            tvDisplay.text = tvDisplay.text.toString().dropLast(1)
            if (tvDisplay.text.isEmpty()) {
                tvDisplay.text = "0"
            }
        }

        // Handling number button clicks
        for (id in btns) {
            val btn = findViewById<Button>(id)
            btn.setOnClickListener {
                if (tvDisplay.text == "0") {
                    tvDisplay.text = ""
                }
                tvDisplay.append(btn.text)

                if (!isOperatorSet) {
                    firstNumber += btn.text
                } else {
                    secondNumber += btn.text
                }
            }
        }


        // Handling operator button clicks
        for (id in operators) {
            val operatorBtn = findViewById<Button>(id)
            operatorBtn.setOnClickListener {
                if (firstNumber.isNotEmpty() && !isOperatorSet) {
                    operator = operatorBtn.text.toString()
                    tvDisplay.append(" $operator ")
                    isOperatorSet = true
                }
            }
        }

        // Equals button - Perform calculation
        equals.setOnClickListener {
            if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && operator.isNotEmpty()) {
                val resultValue = calculate(firstNumber, secondNumber, operator)
                result.text = resultValue.toString()

                // After calculation, reset for the next operation
                firstNumber = resultValue.toString()
                secondNumber = ""
                operator = ""
                isOperatorSet = false
                tvDisplay.text = firstNumber // Display result as next input base
            }
        }
    }

    // Function to perform calculation
    fun calculate(num1: String, num2: String, operator: String): Double {
        val number1 = num1.toDouble()
        val number2 = num2.toDouble()

        return when (operator) {
            "+" -> number1 + number2
            "-" -> number1 - number2
            "*" -> number1 * number2
            "/" -> if (number2 != 0.0) number1 / number2 else Double.NaN // Prevent division by zero
            "%" -> number1 % number2
            else -> 0.0
        }
    }
}

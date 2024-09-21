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

        // UI references
        val tvDisplay = findViewById<TextView>(R.id.tvDisplay)
        val result = findViewById<TextView>(R.id.result)
        val equals = findViewById<Button>(R.id.equal)
        val clear = findViewById<Button>(R.id.Clear)
        val AC = findViewById<Button>(R.id.AC)
        val dot = findViewById<Button>(R.id.Dot)

        // Number buttons array
        val btns = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.Dot
        )

        // Operator buttons array
        val operators = arrayOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnDivide, R.id.btnMultiply, R.id.mod
        )

        // AC button - resets everything
        AC.setOnClickListener {
            tvDisplay.text = "0"
            result.text = "0"
            firstNumber = ""
            secondNumber = ""
            operator = ""
            isOperatorSet = false
        }

        // Backspace button - clears the last digit
        clear.setOnClickListener {
            if (tvDisplay.text.toString().isNotEmpty()) {
                val lastChar = tvDisplay.text.toString().last()

                // Update the number variables accordingly
                if (!isOperatorSet) {
                    firstNumber = if (lastChar == '.') firstNumber.dropLast(1) else firstNumber.dropLast(1)
                } else {
                    secondNumber = if (lastChar == '.') secondNumber.dropLast(1) else secondNumber.dropLast(1)
                }

                // Check if the last character is an operator
                if (lastChar == ' ' && isOperatorSet) {
                    tvDisplay.text = tvDisplay.text.toString().dropLast(3) // Remove operator and spaces
                    operator = ""
                    isOperatorSet = false
                } else {
                    // Normal backspace for numbers
                    tvDisplay.text = tvDisplay.text.toString().dropLast(1)
                }

                // Reset display if it's empty
                if (tvDisplay.text.isEmpty()) {
                    tvDisplay.text = "0"
                    firstNumber = ""
                    secondNumber = ""
                }
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

        // Dot button - to prevent multiple dots in a number
        dot.setOnClickListener {
            if (!isOperatorSet) {
                if (!firstNumber.contains(".")) {
                    firstNumber += "."
                    tvDisplay.append(".")
                }
            } else {
                if (!secondNumber.contains(".")) {
                    secondNumber += "."
                    tvDisplay.append(".")
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

        // Equals button - perform the calculation
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

    // Function to perform real-time calculation
    private fun performRealTimeCalculation(result: TextView) {
        if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && operator.isNotEmpty()) {
            val resultValue = calculate(firstNumber, secondNumber, operator)
            result.text = resultValue.toString()
        }
    }



    // Function to perform calculation
    private fun calculate(num1: String, num2: String, operator: String): Double {
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

package com.example.calculator

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


      val tvDisplay =  findViewById<TextView>(R.id.tvDisplay)
        val AC =  findViewById<Button>(R.id.AC)
        val result =  findViewById<TextView>(R.id.result)
        val scrollView = findViewById<HorizontalScrollView>(R.id.scroll)


        scrollView.post {
            scrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT)
        }


        val btns =  arrayOf(

                    R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9 ,R.id.Dot

        )

        AC.setOnClickListener{
            tvDisplay.setText("0")
            result.setText("0")
        }


        for (id in btns){
            val btn  =  findViewById<Button>(id)
            btn.setOnClickListener{

                if(tvDisplay.text == "0"){
                    tvDisplay.setText(" ")
                }

                tvDisplay.append(btn.text)
            }
        }




fun calculate(num1:String , num2:String , operator:String): Double{
    val number1 = num1.toDouble()
    val number2 = num2.toDouble()

  val value = when (operator){
       "+" -> number1 + number2
       "-" -> number1 - number2
       "*" -> number1 * number2
       "/" -> number1 / number2
       "%" -> number1 % number2
       else -> 0.0
    }

return value
}


    }

}
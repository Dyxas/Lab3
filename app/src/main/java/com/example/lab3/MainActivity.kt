package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var lastValue : String = ""
    var firstValue : String = ""
    var matchValue : String = ""

    var activeValue : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getActiveValue() : String
    {
        return if (activeValue) lastValue else firstValue
    }

    fun setActiveValue(value: String)
    {
        if (activeValue) lastValue = value else firstValue = value
    }

    fun updateValues()
    {
        val text : TextView = findViewById(R.id.textView)
        if (activeValue)
            text.text = lastValue
        else
            text.text = firstValue
    }

    fun secondaryFunctions(view: View) {
        val curValue = (view as Button).text

        if (curValue == "AС") {
            activeValue = false
            lastValue = ""
            firstValue = "0"
        } else if (curValue == "±") {
            var res = (getActiveValue().toDouble() * -1.0)
            var strRes : String = ""
            if (res > res.toInt() || res < res.toInt()) {
                strRes = res.toString()
            } else {
                strRes = res.toInt().toString()
            }

            setActiveValue(strRes)
        }  else {
            if (!activeValue) {
                return
            }
            val percent = lastValue.toFloat() / 100
            val result = firstValue.toFloat() * percent
            lastValue = result.toString()
        }

        updateValues()
    }

    fun isInt(value: Double) : String
    {
        if (value > value.toInt() || value < value.toInt()) {
            return value.toString()
        } else {
            return value.toInt().toString()
        }
    }

    fun calculateValues(view: View) {
        var curValue = (view as Button).text

        if (curValue != "=") {
            activeValue = true
            matchValue = curValue.toString()
        } else {
            if (!activeValue)
                return

            var operation : Double = 0.0
            when(matchValue) {
                "÷" -> operation = firstValue.toDouble() / lastValue.toDouble()
                "✕" -> operation = firstValue.toDouble() * lastValue.toDouble()
                "-" -> operation = firstValue.toDouble() - lastValue.toDouble()
                "+" -> operation = firstValue.toDouble() + lastValue.toDouble()
            }

            lastValue = ""
            firstValue = isInt(operation)
            if (firstValue == "Infinity") {
                firstValue = "0"
                activeValue = false
                val text : TextView = findViewById(R.id.textView)
                text.text = "Ошибка"
                return
            }

            activeValue = false
            updateValues()
        }

    }

    fun inputValue(view: View) {
        val text : TextView = findViewById(R.id.textView)
        var curValue = (view as Button).text

        var curText = getActiveValue()
        if (curValue == ",") {
            if (curText.contains("."))
                return
            else
                curValue = "."
        }

        if (curText == "0") {
            if (curValue == "0") return
            curText = curValue.toString()
        } else {
            curText += curValue
        }
        setActiveValue(curText)
        text.text = getActiveValue()
    }
}
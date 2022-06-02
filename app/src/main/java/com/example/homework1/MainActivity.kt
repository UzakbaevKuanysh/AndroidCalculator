package com.example.homework1

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    private var myData: TextView? = null
    private var myResult: TextView? = null
    private var allClear = true
    private var canAddOperation = false
    private var canAddOpenBracket = true
    private var usedDot = false
    private var myOperations = mutableListOf<String>()
    private var myNumberList = mutableListOf<Double>()
    private var myNumber = ""
    private var myFinalResult = 1.0
    private var existDigit = false
    private var canAddDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }
    override fun onSaveInstanceState(outState: Bundle) {

        outState.putString("myData" , myData!!.text.toString())
        outState.putString("myResult" , myResult!!.text.toString())
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        myData!!.text = savedInstanceState.getString("myData")
        myResult!!.text = savedInstanceState.getString("myResult")
        super.onSaveInstanceState(savedInstanceState)
    }


    private fun initUI() {
        myData = findViewById(R.id.data)
        myResult = findViewById(R.id.result)
        val btn0: Button = findViewById(R.id.btn_0)
        val btn1: Button = findViewById(R.id.btn_1)
        val btn2: Button = findViewById(R.id.btn_2)
        val btn3: Button = findViewById(R.id.btn_3)
        val btn4: Button = findViewById(R.id.btn_4)
        val btn5: Button = findViewById(R.id.btn_5)
        val btn6: Button = findViewById(R.id.btn_6)
        val btn7: Button = findViewById(R.id.btn_7)
        val btn8: Button = findViewById(R.id.btn_8)
        val btn9: Button = findViewById(R.id.btn_9)
        val btnPlus: Button = findViewById(R.id.btn_plus)
        val btnResult: Button = findViewById(R.id.btn_res)
        val btnClear: Button = findViewById(R.id.btn_C)
        val btnBackspace: Button = findViewById(R.id.btn_backspace)
        val btnDivision: Button = findViewById(R.id.btn_division)
        val btnMultiply: Button = findViewById(R.id.btn_multiply)
        val btnSubstraction: Button = findViewById(R.id.btn_substraction)
        val btnMod: Button = findViewById(R.id.btn_percent)
        val btnBrackets: Button = findViewById(R.id.btn_brackets)
        val btnDot: Button = findViewById(R.id.btn_dot)
        btn0.setOnClickListener { addDigits(btn0) }
        btn1.setOnClickListener { addDigits(btn1) }
        btn2.setOnClickListener { addDigits(btn2) }
        btn3.setOnClickListener { addDigits(btn3) }
        btn4.setOnClickListener { addDigits(btn4) }
        btn5.setOnClickListener { addDigits(btn5) }
        btn6.setOnClickListener { addDigits(btn6) }
        btn7.setOnClickListener { addDigits(btn7) }
        btn8.setOnClickListener { addDigits(btn8) }
        btn9.setOnClickListener { addDigits(btn9) }
        btnClear.setOnClickListener { allClear() }
        btnBackspace.setOnClickListener { backSpace() }
        btnPlus.setOnClickListener { addOperation(btnPlus) }
        btnSubstraction.setOnClickListener { addOperation(btnSubstraction) }
        btnMultiply.setOnClickListener { addOperation(btnMultiply) }
        btnDivision.setOnClickListener { addOperation(btnDivision) }
        btnMod.setOnClickListener { addOperation(btnMod) }
        btnBrackets.setOnClickListener { addBrackets(btnBrackets) }
        btnDot.setOnClickListener { addDot(btnDot) }
        btnResult.setOnClickListener { equal() }
    }

    private fun addDigits(view: Button) {
        if (allClear) {
            myData!!.text = ""
            allClear = false
        }
        myData!!.append(view.text)
        myNumber += view.text
        canAddOperation = true
        canAddOpenBracket = false
        canAddDot = true
    }

    private fun addDot(view: Button) {
        if (canAddDot && !usedDot) {
            myData!!.append(view.text)
            canAddDot = false
            usedDot = true
            myNumber += view.text
            canAddOperation = false
            canAddOpenBracket = false
        }
    }

    private fun addOperation(view: Button) {
        if (canAddOperation) {
            myData!!.append(view.text)
            myOperations.add(view.text as String)
            myNumberList.add(myNumber.toDouble())
            usedDot = false
            canAddOperation = false
            canAddDot = false
        }
        myNumber = ""
    }

    private fun addBrackets(view: Button) {
        if (existDigit && !canAddOperation) {
            myData!!.append(view.text)
        } else if (!canAddOpenBracket) {
            myData!!.append(")")
            canAddOperation = true
        }
    }

    private fun backSpace() {
        if (myData!!.length() > 0) {
            myData!!.text = myData!!.text.subSequence(0, myData!!.length() - 1)
        }
    }

    private fun allClear() {
        myData!!.text = "0"
        myResult!!.text = "0"
        allClear = true
        canAddOperation = false
        canAddOpenBracket = true
        existDigit = false
        canAddDot = false
        usedDot = false
        myNumber = ""
        myOperations.clear()
        myNumberList.clear()
        myFinalResult = 1.0
    }

    private fun equal() {
        myNumberList.add(myNumber.toDouble())
        for (index in 0 until myNumberList.size - 1) {
            if (myOperations[index] == "×") {
                myFinalResult = myNumberList[index] * myNumberList[index + 1]
                myNumberList.removeAt(index)
                myNumberList.removeAt(index)
                myNumberList.add(index, 0.0)
                myNumberList.add(index + 1, myFinalResult)
            } else if (myOperations[index] == "÷") {
                myFinalResult = myNumberList[index] / myNumberList[index + 1]
                myNumberList.removeAt(index)
                myNumberList.removeAt(index)
                myNumberList.add(index, 0.0)
                myNumberList.add(index + 1, myFinalResult)
            }
        }
        myNumberList.removeAll(listOf(0.0))
        myOperations.removeAll(listOf("÷", "×"))
        for (index in 0 until myNumberList.size - 1) {
            if (myOperations[index] == "+") {
                myFinalResult = myNumberList[index] + myNumberList[index + 1]
                myNumberList.removeAt(index)
                myNumberList.removeAt(index)
                myNumberList.add(index, 0.0)
                myNumberList.add(index + 1, myFinalResult)
            }
            if (myOperations[index] == "-") {
                myFinalResult = myNumberList[index] - myNumberList[index + 1]
                myNumberList.removeAt(index)
                myNumberList.removeAt(index)
                myNumberList.add(index, 0.0)
                myNumberList.add(index + 1, myFinalResult)
            }
            if (myOperations[index] == "%") {
                myFinalResult = myNumberList[index] % myNumberList[index + 1]
                myNumberList.removeAt(index)
                myNumberList.removeAt(index)
                myNumberList.add(index, 0.0)
                myNumberList.add(index + 1, myFinalResult)
            }
        }
        myResult!!.text = myFinalResult.toString()
    }
}
















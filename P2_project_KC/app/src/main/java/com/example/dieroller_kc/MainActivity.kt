package com.example.dieroller_kc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var rollBtn: Button
    lateinit var numSideBox: EditText
    lateinit var numDiceBox: EditText
    lateinit var outputText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rollBtn = findViewById(R.id.roll_btn)
        rollBtn.setOnClickListener {DoRollBtn()}

        numSideBox = findViewById(R.id.num_side_box)

        numDiceBox = findViewById(R.id.num_dice_box)

        outputText = findViewById(R.id.output_text)
    }

    fun DoRollBtn()
    {
        var rngValue: Int = 0
        var totalRolled: Int = 0

        val diceList= mutableListOf(0)
        diceList.remove(0)

        var numOfDice = numDiceBox?.text?.toString() ?: "4"
        var numOfSides = numSideBox?.text?.toString()

        if (numOfDice == "" || numOfDice == null){
            numOfDice = "4"
        }

        if (numOfSides == "" || numOfSides == null){
            numOfSides = "6"
        }

        for(i in 1..numOfDice.toInt())
        {
            rngValue = Random.nextInt(1, numOfSides.toInt() + 1)
            diceList.add(rngValue)
            totalRolled += rngValue
        }

        outputText.text = "Numbers Rolled: \n${diceList.joinToString(", ")} \n Total Value: ${totalRolled}"
    }

}
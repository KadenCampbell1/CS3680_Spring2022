package com.example.connectionsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var increment_btn: Button
    lateinit var fib_btn: Button
    lateinit var reset_btn: Button
    lateinit var value_text: TextView
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        value_text = findViewById(R.id.value_text)

        increment_btn = findViewById(R.id.increment_btn)
        increment_btn.setOnClickListener{do_increment_btn()}

        reset_btn = findViewById(R.id.reset_btn)
        reset_btn.setOnClickListener{do_reset_btn()}

        fib_btn = findViewById(R.id.fib_btn)
        fib_btn.setOnClickListener{do_fib_btn()}
    }

    fun do_increment_btn(){
        count += 1
        value_text.text = count.toString()
    }

    fun do_reset_btn(){
        count = 0
        value_text.text = count.toString()
    }

    fun do_fib(x: Int): Int{
        if (x <= 0){
            return 0
        }
        else if (x == 1 || x == 2){
            return 1
        }
        return do_fib(x-1) + do_fib(x-2)
    }

    fun do_fib_btn(){
        count = do_fib(count)
        value_text.text = count.toString()
    }
}
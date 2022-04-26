package com.example.fragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), BottomFragment.buttonListener {
    private var top_fragment: TopFragment? = null
    private var bottom_fragment: BottomFragment? = null
    var fm: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fm.beginTransaction().replace(R.id.button_fragment_container, BottomFragment(), "button").commit()
    }

    override fun ButtonOne() {
        if (top_fragment == null){
            fm.beginTransaction().replace(R.id.text_fragment_container, TopFragment(), "text").commit()
        }
    }

    override fun ButtonTwo(value: Int) {
        if (top_fragment == null){
            top_fragment = fm.findFragmentByTag("text") as TopFragment?
        }
        top_fragment?.ShowNumber(value)
    }
}
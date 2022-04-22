package com.example.kc_project_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var dv: DrawView
    lateinit var text_view: TextView
    var gm: GameManager = GameManager()
    var direction_number = 0
    var game_over = false
    var playerPosition = arrayListOf<Int>(2,2)
    var enemy1Position = arrayListOf<Int>(2,2)
    var enemy2Position = arrayListOf<Int>(2,2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dv = findViewById(R.id.drawView)
        text_view = findViewById(R.id.textView)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (game_over){
            return true
        }

        if (event.action == MotionEvent.ACTION_UP) {
            //down = 2
            if (event.y > 750 && event.y > 1150){
                direction_number = 2
            }
            //up = 0
            if (event.y < 1150 && event.y < 750){
                direction_number = 0
            }
            //left = 3
            if (event.y > 750 && event.y < 1150 && event.x < 515){
                direction_number = 3
            }
            //right = 1
            if (event.y > 750 && event.y < 1150 && event.x > 515){
                direction_number = 1
            }

            playerPosition = gm.player_moved(direction_number)
            enemy1Position = gm.enemy1Move()
            enemy2Position = gm.enemy2Move()

            if(gm.win_condition()){
                text_view.text = "YOU WIN!"
                game_over = true
            }
            else if (gm.lose_condition()){
                text_view.text = "YOU LOSE!"
                game_over = true
            }

            dv.set_position(playerPosition, enemy1Position, enemy2Position)
            dv.rect.left = dv.nextX
            dv.rect.top = dv.nextY
            dv.rect.right = dv.nextX + dv.bwidth
            dv.rect.bottom = dv.nextY + dv.bheight
            dv.invalidate()
            return true
        }
        return true
    }
}
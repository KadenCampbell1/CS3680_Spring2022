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
//        var current_pos = arrayListOf<Int>(0,0)
        if (event.action == MotionEvent.ACTION_UP) {
//            Log.i("CS3680", "Touch event ${event.x}, ${event.y}")
//            Log.i("CS3680", "${gm.playerPosition}")
//            gm.p_position()
//            var loc = IntArray(2)
//            dv.getLocationInWindow(loc)
////            var x = event.x - loc[0]
////            var y = event.y - loc[1]
//            Log.i("CS3680", "column: ${dv.gridColumn} row ${dv.gridRow}")
            //down = 2
            if (event.y > 750 && event.y > 1150){
                direction_number = 2
//                if (gm.playerPosition[0] >= 13){
//                    return true
//                }
//                gm.playerPosition[0] += 1
//                current_pos = gm.playerPosition
//                dv.gridColumn += 1
            }
            //up = 0
            if (event.y < 1150 && event.y < 750){
                direction_number = 0
//                if (gm.playerPosition[0] <= 2){
//                    return true
//                }
//                gm.playerPosition[0] -= 1
//                current_pos = gm.playerPosition
//                dv.gridColumn -= 1
            }
            //left = 3
            if (event.y > 750 && event.y < 1150 && event.x < 515){
                direction_number = 3
//                if (gm.playerPosition[1] <= 2){
//                    return true
//                }
//                gm.playerPosition[1] -= 1
//                current_pos = gm.playerPosition
//                dv.gridRow -= 1
            }
            //right = 1
            if (event.y > 750 && event.y < 1150 && event.x > 515){
                direction_number = 1
//                if (gm.playerPosition[1] >= 13){
//                    return true
//                }
//                gm.playerPosition[1] += 1
//                current_pos = gm.playerPosition
//                dv.gridRow += 1
            }
//            gm.set_player_position(current_pos)
//            dv.rect.left = dv.nextX
//            dv.rect.top = dv.nextY
//            dv.rect.right = dv.nextX + dv.bwidth
//            dv.rect.bottom = dv.nextY + dv.bheight
//            gm.enemyMove()
//            Log.i("CS3680", "2column: ${dv.gridColumn} 2row ${dv.gridRow}")
//            dv.invalidate()
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
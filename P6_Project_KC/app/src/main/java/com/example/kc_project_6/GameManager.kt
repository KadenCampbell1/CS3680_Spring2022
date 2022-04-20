package com.example.kc_project_6

import android.util.Log

class GameManager {
    var playerPosition = arrayListOf<Int>(2,2)
    var mapGrid: Array<String> = arrayOf(
        "oooooooooooooooo",
        "oooooooooooooooo",
        "oo~~~~~fffffffoo",
        "oo~wwmmmmfffffoo",
        "oo~wwmm~~~~fffoo",
        "oo~~mm~~~~~fffoo",
        "oommmm~~~~~mffoo",
        "oommm~~~~~wmffoo",
        "oommm~~~~wwmffoo",
        "oomm~~~~wwwmffoo",
        "oomm~~~~wwwmmfoo",
        "oomm~~~~wwwwmfoo",
        "oomm~~~wwwwwmmoo",
        "oomm~~twwwwwmmoo",
        "oooooooooooooooo",
        "oooooooooooooooo"
    )

    var enemy1X_direction = 1
    var enemy1Y_direction = 1
    var e1_position = arrayListOf<Int>(3,5)
    var e2_position = arrayListOf<Int>(13,5)
    var treasure_pos = arrayListOf<Int>(13,6)
    var loop_value = 0

//    fun p_position(): ArrayList<Int> {
////        Log.i("CS3680", "function $playerPosition")
//        return playerPosition
//    }

    fun win_condition(): Boolean{
        return playerPosition == treasure_pos
    }

    fun lose_condition(): Boolean{
        return playerPosition == e1_position || playerPosition == e2_position
    }

    fun player_moved(dir:Int): ArrayList<Int>{
        var next_pos = arrayListOf<Int>(playerPosition[0],playerPosition[1])

        //up
        if (dir == 0){
//                if (playerPosition[0] <= 2){
//                    return playerPosition
//                }
            next_pos[0] = playerPosition[0] - 1
//                current_pos = gm.playerPosition
//                dv.gridColumn -= 1
        }
        //right
        else if (dir == 1){
//                if (playerPosition[1] >= 13){
//                    return playerPosition
//                }
            next_pos[1] = playerPosition[1] + 1
//                current_pos = gm.playerPosition
//                dv.gridRow += 1
        }
        //down
        else if (dir == 2){
//                if (playerPosition[0] >= 13){
//                    return playerPosition
//                }
            next_pos[0] = playerPosition[0] + 1
//            current_pos = gm.playerPosition
//            dv.gridColumn += 1
        }
        //left
        else if (dir == 3){
//                if (playerPosition[1] <= 2){
//                    return playerPosition
//                }
            next_pos[1] = playerPosition[1] - 1
//                current_pos = gm.playerPosition
//                dv.gridRow -= 1
        }
        var letter = mapGrid[next_pos[0]][next_pos[1]].toString()
        if (letter == "o" || letter == "w" || letter == "m"){
            return playerPosition
        }
        else if (letter == "t"){
            //win
            playerPosition = next_pos
            return next_pos
//            playerPosition = next_pos
//            enemy1Move()
//            return next_pos
        }
        else{
            playerPosition = next_pos
            return next_pos
        }
    }

//    fun set_player_position(pos: ArrayList<Int>){
//        playerPosition = pos
//    }

    fun enemy1Move(): ArrayList<Int>{
        if (e1_position[0] <= 2){
            enemy1Y_direction = 1
            if (e1_position[1] <= 2){
                enemy1X_direction = 1
            }
            else if (e1_position[1] >= 13){
                enemy1X_direction = -1
            }
            e1_position[1] += enemy1X_direction
        }
        else if (e1_position[0] >= 13){
            enemy1Y_direction = -1
            if (e1_position[1] <= 2){
                enemy1X_direction = 1
            }
            else if (e1_position[1] >= 13){
                enemy1X_direction = -1
            }
            e1_position[1] += enemy1X_direction
        }
        e1_position[0] += enemy1Y_direction
        Log.i("CS3680", "enemy pos: ${e1_position}")
        return e1_position
    }

    fun enemy2Move(): ArrayList<Int>{
        if (loop_value <= 0){
            loop_value++
            e2_position[0] -= 1
        }
        else if (loop_value <= 2){
            loop_value++
            e2_position[1] += 1
        }
        else if (loop_value <= 3){
            loop_value++
            e2_position[0] += 1
        }
        else if (loop_value <= 4){
            loop_value++
            e2_position[0] -= 1
        }
        else if (loop_value <= 6){
            loop_value++
            e2_position[1] -= 1
        }
        else if (loop_value <= 7){
            e2_position[0] += 1
            loop_value = 0
        }
        return e2_position
//        if (e2_position[0] <= 2){
//            enemy2Y_direction = 1
//            if (e2_position[1] <= 2){
//                enemy2X_direction = 1
//            }
//            else if (e2_position[1] >= 12){
//                enemy2X_direction = -1
//            }
//            e2_position[1] += enemy2X_direction
//        }
//        else if (e2_position[0] >= 12){
//            enemy2Y_direction = -1
//            if (e2_position[1] <= 2){
//                enemy2X_direction = 1
//            }
//            else if (e2_position[1] >= 12){
//                enemy2X_direction = -1
//            }
//            e2_position[1] += enemy2X_direction
//        }
//        e2_position[0] += enemy2Y_direction
//
//        Log.i("CS3680", "enemy pos: ${e2_position}")
    }
}
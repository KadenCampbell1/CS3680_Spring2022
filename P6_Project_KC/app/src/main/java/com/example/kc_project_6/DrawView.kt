package com.example.kc_project_6

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Bitmap
import android.util.Log

class DrawView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {
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

    var gridRow = 0
    var gridColumn = 0
    var nextX = 85F
    var nextY = 300F
    var rect: RectF
    val bwidth = 175
    val bheight = 175
    var forest_bitmap: Bitmap
    var mountain_bitmap: Bitmap
    var plain_bitmap: Bitmap
    var water_bitmap: Bitmap
    var person_bitmap: Bitmap
    var enemy_bitmap: Bitmap
    var out_bitmap: Bitmap
    var treasure_bitmap: Bitmap
    var player_pos = arrayListOf<Int>(2,2)
    var enemy1_pos = arrayListOf<Int>(3,2)
    var enemy2_pos = arrayListOf<Int>(4,2)

    init {
        person_bitmap = BitmapFactory.decodeResource(resources, R.drawable.person, null)
        enemy_bitmap = BitmapFactory.decodeResource(resources, R.drawable.enemy, null)
        forest_bitmap = BitmapFactory.decodeResource(resources, R.drawable.forest, null)
        mountain_bitmap = BitmapFactory.decodeResource(resources, R.drawable.mountain, null)
        plain_bitmap = BitmapFactory.decodeResource(resources, R.drawable.plain, null)
        water_bitmap = BitmapFactory.decodeResource(resources, R.drawable.water, null)
        out_bitmap = BitmapFactory.decodeResource(resources, R.drawable.out, null)
        treasure_bitmap = BitmapFactory.decodeResource(resources, R.drawable.treasure, null)

        rect = RectF()
        rect.left = nextX
        rect.top = nextY
        rect.right = nextX + bwidth
        rect.bottom = nextY + bheight
    }

    override fun onDraw(canvas: Canvas){
        var emptyBitmap: Bitmap = Bitmap.createBitmap(forest_bitmap.width, forest_bitmap.height, forest_bitmap.config)

        if (person_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else if (enemy_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else if (forest_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else if (mountain_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else if (plain_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else if (water_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else if (out_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else if (treasure_bitmap.sameAs(emptyBitmap)) {
            return
        }
        else{
            createGrid(canvas)
        }
    }

    fun createGrid(canvas: Canvas){
        var gridLetter = ""

        gridColumn = player_pos[0] -2
        gridRow = player_pos[1] -2

        for (column in gridColumn..gridColumn + 4){
            for (row in gridRow..gridRow + 4){
                gridLetter = mapGrid[column][row].toString()
                if (gridLetter == "f"){
                    canvas.drawBitmap(forest_bitmap, null, rect, null)
                }
                else if (gridLetter == "m"){
                    canvas.drawBitmap(mountain_bitmap, null, rect, null)
                }
                else if (gridLetter == "~"){
                    canvas.drawBitmap(plain_bitmap, null, rect, null)
                }
                else if (gridLetter == "w"){
                    canvas.drawBitmap(water_bitmap, null, rect, null)
                }
                else if (gridLetter == "t"){
                    canvas.drawBitmap(treasure_bitmap, null, rect, null)
                }
                else{
                    canvas.drawBitmap(out_bitmap, null, rect, null)
                }

                if (row == player_pos[1]){
                    if (column == player_pos[0]){
                        canvas.drawBitmap(person_bitmap, null, rect, null)
                    }
                }
                if (row == enemy1_pos[1]){
                    if (column == enemy1_pos[0]){
                        canvas.drawBitmap(enemy_bitmap, null, rect, null)
                    }
                }
                if (row == enemy2_pos[1]){
                    if (column == enemy2_pos[0]){
                        canvas.drawBitmap(enemy_bitmap, null, rect, null)
                    }
                }
                rect.left += bwidth
                rect.right += bwidth
            }
            rect.left = nextX
            rect.right = nextX + bwidth
            rect.top += bheight
            rect.bottom += bheight
        }

    }

    fun set_position(p_pos: ArrayList<Int>, e1_pos: ArrayList<Int>, e2_pos:  ArrayList<Int>){
        player_pos = p_pos
        enemy1_pos = e1_pos
        enemy2_pos = e2_pos
    }
}
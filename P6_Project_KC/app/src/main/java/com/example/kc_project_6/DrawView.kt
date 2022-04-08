package com.example.kc_project_6

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Bitmap
import android.util.Log
import android.view.MotionEvent


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
        "oomm~~~wwwwwmmoo",
        "oooooooooooooooo",
        "oooooooooooooooo"
    )

    var playerPosition = arrayListOf<Int>(2,2)
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
    var out_bitmap: Bitmap

    init {
        person_bitmap = BitmapFactory.decodeResource(resources, R.drawable.person, null)
        forest_bitmap = BitmapFactory.decodeResource(resources, R.drawable.forest, null)
        mountain_bitmap = BitmapFactory.decodeResource(resources, R.drawable.mountain, null)
        plain_bitmap = BitmapFactory.decodeResource(resources, R.drawable.plain, null)
        water_bitmap = BitmapFactory.decodeResource(resources, R.drawable.water, null)
        out_bitmap = BitmapFactory.decodeResource(resources, R.drawable.out, null)

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
        else{
//            canvas.drawBitmap(forest_bitmap, nextX, nextY, null)
            createGrid(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            Log.i("CS3680", "Touch event ${event.x}, ${event.y}")
            Log.i("CS3680", "$playerPosition")
            if (event.y > 1000){
                if (playerPosition[0] >= 13){
                    return true
                }
                playerPosition[0] += 1
                gridColumn += 1
            }
            if (event.y < 475){
                if (playerPosition[0] <= 2){
                    return true
                }
                playerPosition[0] -= 1
                gridColumn -= 1
            }
            if (event.y > 475 && event.y < 1000 && event.x < 515){
                if (playerPosition[1] <= 2){
                    return true
                }
                playerPosition[1] -= 1
                gridRow -= 1
            }
            if (event.y > 475 && event.y < 1000 && event.x > 515){
                if (playerPosition[1] >= 13){
                    return true
                }
                playerPosition[1] += 1
                gridRow += 1
            }
            rect.left = nextX
            rect.top = nextY
            rect.right = nextX + bwidth
            rect.bottom = nextY + bheight
            invalidate()
            return true
        }
        return true
    }

    fun createGrid(canvas: Canvas){
        var gridLetter = ""

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
                else{
                    canvas.drawBitmap(out_bitmap, null, rect, null)
                }
                if (row == playerPosition[1]){
                    if (column == playerPosition[0]){
                        canvas.drawBitmap(person_bitmap, null, rect, null)
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
//        canvas.drawBitmap(forest_bitmap, null, rect, null)
//        rect.left += 150
//        rect.right += 150
//        canvas.drawBitmap(mountain_bitmap, null, rect, null)
//        Log.i("CS3680", "${mapGrid[1][3]}")
    }
}
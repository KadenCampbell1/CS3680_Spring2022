package com.example.p5_project_kc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

@Serializable
class Celestial(val type: String, val name: String, val moonCount: String, val distanceFromSun: String, val diameterKm: String, val satellites: Array<Satellites> = arrayOf(Satellites("null", "null")))

@Serializable
class Satellites(@SerialName("name") val sName: String, @SerialName("diameterKm") val sDiameterKm: String)

class MainActivity : AppCompatActivity() {
    lateinit var satName: TextView
    lateinit var satDiam: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        satName = findViewById(R.id.SatelliteNameTv)
        satDiam = findViewById(R.id.SatelliteDiameterTv)

        CoroutineScope(IO).launch {
            getData()
        }
    }

    suspend fun getData() {
        withContext(IO) {
            val text = URL("https://roversgame.net/cs3680/planets.json").readText()
            val JsonData = Json.decodeFromString<List<Celestial>>(text)
            Log.i("CS3680", JsonData.toString())

            withContext(Main) {
                for (i in JsonData){
                    if(i.name == "Jupiter"){
                        satName.text = "Satellite Name: \n${i.satellites[2].sName}"
                        satDiam.text = "Satellite Diameter: \n${i.satellites[2].sDiameterKm} km"
                    }
                }
            }
        }
    }
}
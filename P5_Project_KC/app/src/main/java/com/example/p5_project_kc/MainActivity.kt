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

//val jsonChars = """
//        [
//        {"name": "Lumi Astrolay", "class": "bard", "race": "halfling"},
//        {"name": "Nisticheski", "class": "cleric", "race": "dragonborn"}
//        ]
//        """.trimIndent()

//val dataStuff = """
//        [
//        {"type": "planet", "name": "Mercury", "moonCount": "0", "distanceFromSun": "0.39", "diameterKm": "4878"},
//        {"type": "planet", "name": "Venus", "moonCount": "0", "distanceFromSun": "0.72", "diameterKm": "12104"},
//        {"type": "planet", "name": "Earth", "moonCount": "1", "distanceFromSun": "1", "diameterKm": "12756"},
//        {"type": "planet", "name": "Mars", "moonCount": "2", "distanceFromSun": "1.52", "diameterKm": "6787",
//        "satellites": [
//            {"name": "Phobos", "diameterKm": "27"},
//            {"name": "Deimos", "diameterKm": "16"}
//        ]
//        },
//        {"type": "planet", "name": "Jupiter", "moonCount": "63", "distanceFromSun": "5.2", "diameterKm": "142800",
//        "satellites": [
//            {"name": "Io", "diameterKm": "3630"},
//            {"name": "Europa", "diameterKm": "3138"},
//            {"name": "Ganymede H2021", "diameterKm": "5262"},
//            {"name": "Callisto", "diameterKm": "4800"}
//        ]
//        },
//        {"type": "planet", "name": "Saturn", "moonCount": "62", "distanceFromSun": "9.54", "diameterKm": "120000"},
//        {"type": "planet", "name": "Uranus", "moonCount": "27", "distanceFromSun": "19.8", "diameterKm": "51118"},
//        {"type": "planet", "name": "Neptune", "moonCount": "13", "distanceFromSun": "30.06", "diameterKm": "49528"},
//        {"type": "dwarfPlanet", "name": "Pluto", "moonCount": "3", "distanceFromSun": "39.44", "diameterKm": "2300",
//         "satellites": [
//             {"name": "Charon", "diameterKm": "1207"}
//        ]
//        },
//        {"type": "dwarfPlanet", "name": "Ceres", "moonCount": "0", "distanceFromSun": "2.76", "diameterKm": "974.6"},
//        {"type": "dwarfPlanet", "name": "Eris", "moonCount": "1", "distanceFromSun": "67.67", "diameterKm": "3000"}
//        ]
//
//        2022-03-27 16:00:18.907 25367-25406/com.example.p5_project_kc I/CS3680: [[, {"type": "planet", "name": "Mercury", "moonCount": "0", "distanceFromSun": "0.39", "diameterKm": "4878"},, {"type": "planet", "name": "Venus", "moonCount": "0", "distanceFromSun": "0.72", "diameterKm": "12104"},, {"type": "planet", "name": "Earth", "moonCount": "1", "distanceFromSun": "1", "diameterKm": "12756"},, {"type": "planet", "name": "Mars", "moonCount": "2", "distanceFromSun": "1.52", "diameterKm": "6787",, "satellites": [,     {"name": "Phobos", "diameterKm": "27"},,     {"name": "Deimos", "diameterKm": "16"}, ], },, {"type": "planet", "name": "Jupiter", "moonCount": "63", "distanceFromSun": "5.2", "diameterKm": "142800",, "satellites": [,     {"name": "Io", "diameterKm": "3630"},,     {"name": "Europa", "diameterKm": "3138"},,     {"name": "Ganymede H2021", "diameterKm": "5262"},,     {"name": "Callisto", "diameterKm": "4800"}, ], },, {"type": "planet", "name": "Saturn", "moonCount": "62", "distanceFromSun": "9.54", "diameterKm": "120000"},, {"type": "planet", "name": "Uranus", "moonCount": "27", "distanceFromSun": "19.8", "diameterKm": "51118"},, {"type": "planet", "name": "Neptune", "moonCount": "13", "distanceFromSun": "30.06", "diameterKm": "49528"},, {"type": "dwarfPlanet", "name": "Pluto", "moonCount": "3", "distanceFromSun": "39.44", "diameterKm": "2300", ,  "satellites": [,      {"name": "Charon", "diameterKm": "1207"}, ], },, {"type": "dwarfPlanet", "name": "Ceres", "moonCount": "0", "distanceFromSun": "2.76", "diameterKm": "974.6"},, {"type": "dwarfPlanet", "name": "Eris", "moonCount": "1", "distanceFromSun": "67.67", "diameterKm": "3000"}, ], ]
//        """

//@Serializable
//class GameChar(val name: String, @SerialName("class") val gclass: String, val race: String)

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

//        val gchars = Json.decodeFromString<List<GameChar>>(jsonChars)
//        Log.i("CS3680", "${gchars[0].name}")

        CoroutineScope(IO).launch {
            getData()
        }
    }

    suspend fun getData() {
        withContext(IO) {
            val text = URL("https://roversgame.net/cs3680/planets.json").readText()
//            val lines = text.lines()
//            Log.i("CS3680", lines.toString())
//            for(i in text.lines()){
//            Log.i("CS3680", i.toString())
//        }
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
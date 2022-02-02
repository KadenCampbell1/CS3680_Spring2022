package com.example.connectionsdemo
import kotlin.random.Random
/**
 * Creates the stats for a D&D 5e character
 */

class Die()
{
    var rngValue: Int = 0

    fun roll(numOfSides: Int, numOfDice: Int): List<Int>
    {
        val diceList= mutableListOf(0)
        diceList.remove(0)

        for(i in 1..numOfDice)
        {
            rngValue = Random.nextInt(1, numOfSides + 1)
            diceList.add(rngValue)
        }
        return diceList
    }

}

fun createCharacter(): MutableList<Int>
{
    var total = 0

    val rolls = mutableListOf(0)
    rolls.remove(0)

    for(i in Die().roll(6,4))
    {
        rolls.add(i)
    }

    rolls.sortDescending()
    rolls.removeLast()

    println(rolls)

    return rolls
}

fun main()
{
    var total = 0
    var numOfStats = 6
    var numOfCharacters = 10
    var finalStats = mutableListOf(0)
    finalStats.remove(0)

    for(i in 1..numOfCharacters)
    {
        println(i)

        for(i in 1..numOfStats)
        {
            for(i in createCharacter())
            {
                total += i
            }

            finalStats.add(total)
            total = 0
        }

        println(finalStats)
        finalStats = mutableListOf(0)
        finalStats.remove(0)
    }
}
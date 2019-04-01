package com.allaboutscala.chapter.one.tutorial_04

object ForLoopStuff extends App {

  println("making a for loop")
  for (count <- 1 until 5) {
    println(s"nurga durga $count")
  }
  println("\nStep 3: Filter values using if conditions in for loop")
  val donutIngredients = List("flour", "sugar", "egg yolks", "syrup", "flavouring")
  for(ingredient <- donutIngredients if ingredient == "sugar"){
    println(s"Found sweetening ingredient = $ingredient")
  }

  println("\nStep 4: Filter values using if conditions in for loop and return the result back using the yield keyword")
  val sweeteningIngredients = for {
    ingredient <- donutIngredients
    if (ingredient == "sugar" || ingredient == "syrup")
  } yield ingredient
  println(s"Sweetening ingredients = $sweeteningIngredients")

  println("\nStep 3: Filter values using if conditions in for loop")
  val FFXIVClasses = List("Summoner", "bard", "Scholar", "Warrior")
  for (ultimateClass <- FFXIVClasses if ultimateClass == "Summoner") {
    println(s"Found best class = $ultimateClass")
  }

  println("\nStep 4: yeild thing")
  val betterClass =  for {
    betterClass <- FFXIVClasses
    if (betterClass == "Summoner" || betterClass == "bard")
  }
    yield betterClass
  println(s"Found best class = $betterClass")

  val twoDimensionalArray = Array.ofDim[String](2,2)
  twoDimensionalArray(0)(0) = "ruin "
  twoDimensionalArray(0)(1) = "ruin2"
  twoDimensionalArray(1)(0) = "ruin3"
  twoDimensionalArray(1)(1) = "ruin4"

  for { x <- 0 until 2
        y <- 0 until 2}
    println(s"Donut ingredient at index ${(x,y)} = ${twoDimensionalArray(x)(y)}")

}

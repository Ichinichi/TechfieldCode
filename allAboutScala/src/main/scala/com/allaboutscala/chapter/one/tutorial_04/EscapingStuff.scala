package com.allaboutscala.chapter.one.tutorial_04

object EscapingStuff extends App {

  println("\nStep 2: Using backslash to escpae quotes")
  val donutJson2: String = "{\"donut_name\":\"Glazed Donut\",\"taste_level\":\"Very Tasty\",\"price\":2.50}"
  println(s"donutJson2 = $donutJson2")

  println("\nStep 3: Using triple quotes \"\"\" to escape characters")
  val donutJson3: String = """{"donut_name":"Glazed Donut","taste_level":"Very Tasty","price":2.50}"""
  println(s"donutJson3 = $donutJson3")

  val myJSONexample: String =
    """
      |{
      |"mammal" : "tiger"
      |"fish" : "barracuda"
      |}
    """.stripMargin
  println(s"$myJSONexample")

  println("Step 1: Using if clause as a statement")
  val numberOfPeople = 21
  val donutsPerPerson = 2
  val defaultDonutsToBuy = 8

  println("\nStep 3: Using if, else if, and else clause as a statement")
  if (numberOfPeople > 10) {
    println(s"Number of donuts to buy = ${numberOfPeople * donutsPerPerson}")
  }
  else if (numberOfPeople == 0) {
    println("Number of people is zero.")
    println("No need to buy donuts.")
  }
  else {
    println(s"Number of donuts to buy = $defaultDonutsToBuy")
  }

  println("\nStep 4: Using if and else clause as expression")
  val numberOfDonutsToBuy = if (numberOfPeople > 10) numberOfPeople * donutsPerPerson else defaultDonutsToBuy
  println(s"Number of donuts to buy = $numberOfDonutsToBuy")

  println("\nStep 4: Using derp clause as expression")
  val derp = if (numberOfPeople > 10) "derp" else defaultDonutsToBuy
  println(s"Number of donuts to buy = $derp")


}


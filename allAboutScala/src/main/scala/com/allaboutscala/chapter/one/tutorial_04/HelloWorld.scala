package com.allaboutscala.chapter.one.tutorial_04

object HelloWorld extends App {
  var donuts: String = "dacks";
  val favoriteDonut: String = "Glazed Donut";
  case class Candy(name: String, size: Double, flavor: String);
  case class Donut(name: String, tasteLevel: String);
  val creamSaver: Candy = Candy("hardcandy", 2.70, "orange")
  val favoriteDonut2: Donut = Donut("Glazed Donut", "Very Tasty");
  val donutName: String = "Vanilla Donut"
  val donutTasteLevel: String = "Tasty"
  donuts = "black";

  println("Hello batches, from Scala");
  println("Step 1: Using String interpolation to print a variable");
  println(s"My favorite donut = $favoriteDonut but not $donuts");

  println("\nStep 2: Using String interpolation on object properties");
  println(s"My favorite donut name = ${favoriteDonut2.name}, tasteLevel = ${favoriteDonut2.tasteLevel}");
  println(f"stuff about my candy: type is ${creamSaver.name} and its size is ${creamSaver.size}%.2f")
  println(f"${creamSaver.name}%20s ${creamSaver.name}")

  println("\nStep 4: Using String interpolation for formatting text")
  println(f"$donutName%20s $donutTasteLevel")

  println("\nStep 6: Using raw interpolation")
  println(raw"Favorite donut\t$donutName")


}

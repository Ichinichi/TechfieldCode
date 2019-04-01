package com.allaboutscala.chapter.one.tutorial_06

object HelloWorldWithArguments extends App {
  println("this is hello world from hw with arguements");
  println("command line aruements are :")
  println(args.mkString(", "))
}

package ru.ifmo.sd.aspects

/**
  * Created by sugakandrey.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val fibs = new Fibs()
    println(fibs.memoizedFibs(50))
    println(fibs.slowFibs(15))
    println(fibs.iterativeFibs(100))
  }
}

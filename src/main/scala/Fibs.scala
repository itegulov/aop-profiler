package ru.ifmo.sd.aspects

import scala.collection.mutable

/**
  * Created by sugakandrey.
  */
class Fibs() {

  def slowFibs(n: Int): BigInt = n match {
    case 1 | 0 => n
    case _     => slowFibs(n - 1) + slowFibs(n - 2)
  }

  private val cache = mutable.HashMap.empty[BigInt, BigInt]

  def memoizedFibs(n: BigInt): BigInt = n match {
    case _ if n == 0 || n == 1 => n
    case _ =>
      val fst = cache.getOrElseUpdate(n - 1, memoizedFibs(n - 1))
      val snd = cache.getOrElseUpdate(n - 2, memoizedFibs(n - 2))
      fst + snd
  }

  def iterativeFibs(n: Int): BigInt = {
    var (fst, snd, i): (BigInt, BigInt, Int) = (0, 1, n)

    while (i > 0) {
      val tmp = fst + snd
      fst = snd
      snd = tmp
      i -= 1
    }
    fst
  }

}

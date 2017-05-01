package ru.ifmo.sd.aspects

import org.aspectj.lang.{JoinPoint, ProceedingJoinPoint}
import org.aspectj.lang.annotation.{After, Around, Aspect, Pointcut}

import scala.concurrent.duration._
import scala.collection.mutable

/**
  * Created by sugakandrey.
  */
@Aspect
class AspectProfiler {
  private case class MethodInvocation(methodName: String, timeSpent: Duration)

  private val profileCache = mutable.HashSet.empty[MethodInvocation]

  @Pointcut("execution(* ru.ifmo.sd.aspects.Main$.main(..))")
  def report(): Unit = {}

  @After("report()")
  def printReport(): Unit =
    profileCache
      .groupBy(_.methodName)
      .foreach {
        case (name, calls) =>
          val size      = calls.size
          val durations = calls.map(_.timeSpent.toMillis)
          val total     = durations.sum
          println(
            s"""
               |Method $name report: 
               |  Called $size times
               |  Total time spent: ${total}ms
               |  Average execution time: ${total * 1f / size}ms
             """.stripMargin
          )
      }

  @Pointcut("execution(* ru.ifmo.sd.aspects.Fibs.*(..))")
  def profile(): Unit = {}

  @Around("profile()")
  def gatherTimeData(pjp: ProceedingJoinPoint): Object = {
    val start  = System.nanoTime()
    val result = pjp.proceed()
    val end    = System.nanoTime()
    profileCache += MethodInvocation(pjp.toName, Duration.fromNanos(end - start))
    result
  }

  implicit class RichJoinPoint(jp: JoinPoint) {
    def toName: String = s"${jp.getThis.getClass.getSimpleName}.${jp.getSignature.getName}"
  }
}

package com.paulbutcher

import akka.actor._
import collection.mutable.HashMap

class ConsumerFlowControl extends Actor {

  val counts = new HashMap[String, Int].withDefaultValue(0)

  def receive = {
    case Page(title, text) =>
      sender ! Processed
      for (word <- Words(text))
        counts(word) += 1
  }
}
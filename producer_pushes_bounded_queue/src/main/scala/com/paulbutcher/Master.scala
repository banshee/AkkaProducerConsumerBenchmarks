package com.paulbutcher

import akka.actor._
import akka.routing.{FromConfig, RoundRobinRouter}

class Master extends Actor {

  val consumers = context.actorOf(Props[Consumer].
    withRouter(FromConfig()).
    withDispatcher("consumer-dispatcher"), "consumers")
  val producer = context.actorOf(Props(new Producer(consumers)), "producer")

  context.watch(consumers)

  def receive = {
    case Terminated(`consumers`) => context.system.shutdown
  }
}
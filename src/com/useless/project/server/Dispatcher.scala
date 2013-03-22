package com.useless.project.server

import actors.Actor

object Dispatcher extends Actor {

  var players = List[Player]();

  def act {
    loop {
      receive {

        // EVENT New player
        case NewPlayer(a) =>
          players = players :+ a

        // EVENT New talk
        case Talk(player, message) =>
          players foreach { _ ! Talk(player, message) }

      }
    }
  }

}
package com.useless.project.server

import java.net._
import java.io._

case class Echo(socket: Socket)

case class Talk(player: Player, message: String)

case class NewPlayer(player: Player)
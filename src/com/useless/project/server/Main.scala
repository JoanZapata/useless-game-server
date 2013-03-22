package com.useless.project.server

import java.net._
import java.io._

import scala.actors._
import scala.actors.Actor._

object Main extends App {

  // Connection socket
  val serverSocket = new ServerSocket(8888)

  // Start dispatcher actor
  Dispatcher.start;

  // Connection loop
  while (true) {

    println("Awaiting connection...")
    val clientSocket = serverSocket.accept()

    // On connection, create a new anonymous player actor
    val player = new Player()

    // Start it
    player.start
    
    // Send it the client socket
    player ! Echo(clientSocket)

  }

}
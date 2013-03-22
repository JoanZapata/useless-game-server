package com.useless.project.server

import java.net._
import java.io._

import scala.actors._
import scala.actors.Actor._

// A player is an actor that listen for events from the outside world
// or from the socket behind it.
class Player extends Actor {

  // Base properties of all players
  var name: String = ""
  var x: Int = 0;
  var y: Int = 0;

  // Technical properties
  private var socket: Socket = null;

  implicit def inputStreamWrapper(in: InputStream) =
    new BufferedReader(new InputStreamReader(in))

  implicit def outputStreamWrapper(out: OutputStream) =
    new PrintWriter(new OutputStreamWriter(out))

  def print(s: String) {
    val out: PrintWriter = socket.getOutputStream()
    out.println(s)
    out.flush()
  }

  def read(): String = {
    val in: BufferedReader = socket.getInputStream()
    in.readLine()
  }

  // This is where the actor
  // reacts to outside events
  def act() {
    loop {
      receive {
        
        // Supposed to be sent only once
        // Set the socket for this player
        case Echo(socket) =>
          this.socket = socket
          // Register is blocking, start in a new actor
          actor { register() }

        // Someone talked
        case Talk(player, message) =>
          print(player.name + ": " + message)
          
      }
    }
  }

  def register() {

    // Read name
    print("Who ?")
    name = read()
    print("Welcome " + name)

    // Add to the dispatcher server
    Dispatcher ! NewPlayer(this)

    // Start read loop
    while (true) {
      val line = read()
      Dispatcher ! Talk(this, line)
    }
  }

}
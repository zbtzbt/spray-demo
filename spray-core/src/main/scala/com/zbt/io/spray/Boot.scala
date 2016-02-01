package com.zbt.io.spray

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import com.zbt.io.spray.actors.UserService
import spray.can.Http

/**
 * Created by Frank Zhang on 16/2/1.
 */
object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("spray-can")
  // create and start our service actor
  val service = system.actorOf(Props[UserService], "userService")
  // implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 9080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = system.settings.config.getString("spray.interface"), port = system.settings.config.getInt("spray.port"))

}

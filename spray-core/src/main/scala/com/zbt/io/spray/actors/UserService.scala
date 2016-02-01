package com.zbt.io.spray.actors

import akka.actor.{ActorRefFactory, Actor, ActorLogging}
import com.alibaba.fastjson.JSON
import spray.routing.{Route, HttpService}
import spray.http.MediaTypes._


/**
 * Created by Frank Zhang on 16/1/31.
 */
class UserService extends HttpService with Actor with ActorLogging  {

  val config = context.system.settings.config

  implicit val system = context.system

  override implicit def actorRefFactory: ActorRefFactory = context

  override def receive: Receive = runRoute(route)

  def route: Route = {
      singleSlashRoute ~
      userRoute
  }

  val singleSlashRoute: Route = pathSingleSlash {
    get {
      respondWithMediaType(`text/html`){
        complete {
            <html>
              <h1>欢迎使用spray demo</h1>
              <br/>
              <p>本demo演示http请求json数据</p>
              <br/>
              <ul>
                <li><a href="/user/findById?id=1">获取用户信息</a></li>
                <li><a href="/user/save?id=1">新增用户信息</a></li>
              </ul>
            </html>
        }
      }
    }
  }

  val userRoute: Route = path("user" / Segment) { key =>
      get {
        key match {
          case "findById" =>
            parameters("id") { id=>
              complete {
                val resultMap = new java.util.HashMap[String,AnyRef]()
                resultMap.put("id",id)
                resultMap.put("name","duck")
                resultMap.put("gender","male")
                resultMap.put("description","I am a smart durk!")
                JSON.toJSONString(resultMap,true)
              }
            }

          case _ => reject
        }
      } ~
      post {
        key match {
          case "save" =>
            parameters("id") { id =>
              complete {
                "post success,this method is not implement"
              }
            }
          case _ => reject
        }
      }
    }

}

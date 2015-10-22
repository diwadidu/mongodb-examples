package com.torstenmuller.app

import org.scalatra._
import com.mongodb.casbah.Imports._

class DoggieApi(mongo: MongoCollection) extends ScalaStack {

  get("/") {

    val cursor = mongo.find()
    cursor.next()
//    for { x <- mongo} yield x

  }
  
}

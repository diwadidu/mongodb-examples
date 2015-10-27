package com.torstenmuller.app

import org.scalatra._
import org.scalatra.json._
import com.mongodb.casbah.Imports._
import org.json4s.{DefaultFormats, Formats}
//import com.torstenmuller.app.Dog


class DoggieApi(mongo: MongoCollection) extends ScalaStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  override protected def renderPipeline = renderMongo orElse super.renderPipeline

  get("/") {

    for (rec <- mongo.find())
      yield new Dog(rec)

  }


  def renderMongo = {
    case dbo: DBObject =>
      contentType = "application/json"
      dbo.toString

    case xs: TraversableOnce[_] => // handles a MongoCursor, be aware of type erasure here
      contentType = "application/json"
      val ls = xs map (x => x.toString) mkString(",")
      "[" + ls + "]"

  }: RenderPipeline


}

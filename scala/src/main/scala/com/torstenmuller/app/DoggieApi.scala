package com.torstenmuller.app

import org.scalatra._
import org.scalatra.json._
import com.mongodb.casbah.Imports._
import org.json4s.{DefaultFormats, Formats}


class DoggieApi(mongo: MongoCollection) extends ScalaStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  override protected def renderPipeline = renderMongo orElse super.renderPipeline

  get("/") {

    mongo.find()
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

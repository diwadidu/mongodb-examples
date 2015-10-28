package com.torstenmuller.app

import org.scalatra._
import org.scalatra.json._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId

//import scala.util.parsing.json._

import org.json4s.{DefaultFormats, Formats}
//import org.json4s.native.JsonMethods._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST._

class DoggieApi(mongo: MongoCollection) extends ScalaStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  override protected def renderPipeline = renderMongo orElse super.renderPipeline

  before() {
    contentType = formats("json")
  }


  /**
   * Get a list of all the dogs in the database
   */
  get("/") {
    for (rec <- mongo.find())
      yield this.toOutput(rec)
  }


  /**
   * Get a single dog by ID
   */
  get("/:id") {

    val q = MongoDBObject("_id" -> new ObjectId(params("id")))
    this.toOutput(mongo.findOne(q).get)
  }


  post("/") {
    val rec = this.fromInput(request.body)
    mongo.save(rec)
    this.toOutput(rec)
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


  def fromInput(jsonString: String): MongoDBObject = {

    val jsonObj = org.json4s.native.JsonMethods.parse(jsonString)

    MongoDBObject(
      "name" -> (jsonObj \ "name").extract[String],
      "age" -> (jsonObj \ "age").extract[String],
      "breed" -> (jsonObj \ "breed").extract[String]
    )
  }


  def toOutput(record: DBObject): DBObject = {

    val id = record.as[ObjectId]("_id")

    MongoDBObject(
      "id" -> id.toString(),
      "name" -> record.get("name"),
      "age" -> record.as[Byte]("age"),
      "breed" -> record.get("breed")
    )
  }


}

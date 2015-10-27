package com.torstenmuller.app

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import com.mongodb.casbah.Imports._


class Dog(rec: DBObject) {

  private val dogRec = formatRecord(rec)

  private def formatRecord(record: DBObject): DBObject = {
    val objMap = Map(
      "id" -> record.get("_id"),
      "name" -> record.get("name"),
      "age" -> record.get("age"),
      "breed" -> record.get("breed")
    )
    val obj = DBObject()
    obj.putAll(objMap)
    obj
  }

  override def toString(): String = {
    dogRec.toString()
  }

}

// Copyright (C) 2017-2018 geoHeil
package at.geoheil.app

import at.geoheil.utils.SparkBaseRunner

import scala.language.postfixOps

object SparkJob extends SparkBaseRunner {
  val spark = createSparkSession(this.getClass.getName)

  println("here")
}

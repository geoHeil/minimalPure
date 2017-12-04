// Copyright (C) 2017-2018 geoHeil
package at.geoheil.utils

import at.geoheil.app.SparkJob.spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{ lit, lower }

object HiveUtils {

  def createOrReplaceFunction(functionName: String, fullyQualifiedClassName: String, namespace: String = "default",
    spark: SparkSession) = {
    if (checkIfFunctionAvailable("ST_Bin", namespace, spark) > 0) {
      spark.sql(s"drop function $functionName")
      createHiveFunction(functionName, fullyQualifiedClassName)
    } else {
      createHiveFunction(functionName, fullyQualifiedClassName)
    }
  }

  private def checkIfFunctionAvailable(functionName: String, namespace: String, spark: SparkSession) = {
    import spark.implicits._
    spark.sql("SHOW FUNCTIONS").filter('function === lower(lit(s"$namespace.$functionName"))).count
  }

  private def createHiveFunction(functionName: String, fullyQualifiedClassName: String) = {
    spark.sql(s"create function $functionName as '$fullyQualifiedClassName'")
  }

}

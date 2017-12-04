// Copyright (C) 2017-2018 geoHeil

package at.geoheil.utils

import org.apache.spark.SparkConf
import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.sql.SparkSession
import pureconfig._

object ConfigurationUtils {

  def loadConfiguration[T <: Product](implicit reader: ConfigReader[T]): T = {
    loadConfig[T] match {
      case Right(s) => s
      case Left(l) => throw new ConfigurationInvalidException(s"Failed to start. There is a problem with the configuration: $l")
    }
  }

  def createSparkSession(appName: String): SparkSession = {
    SparkSession
      .builder()
      .config(createConf(appName))
      .enableHiveSupport()
      .getOrCreate()
  }

  def createConf(appName: String): SparkConf = {
    new SparkConf()
      .setAppName(appName)
      .setIfMissing("spark.master", "local[*]")
      .setIfMissing("spark.driver.memory", "12G")
      .setIfMissing("spark.default.parallelism", "12")
      .setIfMissing("spark.driver.maxResultSize", "1G")
      .setIfMissing("spark.speculation", "true")
      .setIfMissing("spark.serializer", classOf[KryoSerializer].getCanonicalName)
      .setIfMissing("spark.kryoserializer.buffer.max", "1G")
      .setIfMissing("spark.kryo.unsafe", "true")
      .setIfMissing("spark.kryo.referenceTracking", "false")
      .setIfMissing("spark.driver.extraJavaOptions", "-XX:+UseG1GC")
      .setIfMissing("spark.executor.extraJavaOptions", "-XX:+UseG1GC")
      .setIfMissing("spark.memory.offHeap.enabled", "true")
      .setIfMissing("spark.memory.offHeap.size", "1g")
    //  .registerKryoClasses(Array(
    //  //TODO register all used classes here, force Kryo serialization and register all classes
    //  classOf[PortableDataStream]))
  }

}

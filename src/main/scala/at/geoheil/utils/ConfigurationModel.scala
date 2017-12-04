// Copyright (C) 2017-2018 geoHeil

package at.geoheil.utils

case class ConfigurationInvalidException(
  private val message: String = "",
  private val cause: Throwable = None.orNull)
  extends Exception(message, cause)

sealed case class SampleConfig(input: String, output: String)

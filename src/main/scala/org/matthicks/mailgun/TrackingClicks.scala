package org.matthicks.mailgun

import fabric.rw.ReaderWriter

sealed trait TrackingClicks

object TrackingClicks {
  private lazy val map = Map("default" -> Default, "yes" -> Yes, "no" -> No, "htmlonly" -> HTMLOnly)

  implicit val rw: ReaderWriter[TrackingClicks] = ReaderWriter(_.getClass.getSimpleName.toLowerCase, v => map(v.asStr.value))

  case object Default extends TrackingClicks
  case object Yes extends TrackingClicks
  case object No extends TrackingClicks
  case object HTMLOnly extends TrackingClicks
}
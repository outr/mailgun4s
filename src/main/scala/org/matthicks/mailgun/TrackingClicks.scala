package org.matthicks.mailgun

import fabric._
import fabric.define.DefType
import fabric.rw.RW

sealed trait TrackingClicks

object TrackingClicks {
  private lazy val map = Map("default" -> Default, "yes" -> Yes, "no" -> No, "htmlonly" -> HTMLOnly)

  implicit val rw: RW[TrackingClicks] = RW.from(_.getClass.getSimpleName.toLowerCase, v => map(v.asStr.value), DefType.Str)

  case object Default extends TrackingClicks
  case object Yes extends TrackingClicks
  case object No extends TrackingClicks
  case object HTMLOnly extends TrackingClicks
}
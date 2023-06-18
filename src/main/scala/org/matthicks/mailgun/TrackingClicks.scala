package org.matthicks.mailgun

import fabric.define.DefType
import fabric._
import fabric.rw.RW

sealed trait TrackingClicks

object TrackingClicks {
  private lazy val map = Map("default" -> Default, "yes" -> Yes, "no" -> No, "htmlonly" -> HTMLOnly)

  implicit val rw: RW[TrackingClicks] = RW.from(
    r = _.getClass.getSimpleName.toLowerCase,
    w = v => map(v.asStr.value),
    d = DefType.Str
  )

  case object Default extends TrackingClicks
  case object Yes extends TrackingClicks
  case object No extends TrackingClicks
  case object HTMLOnly extends TrackingClicks
}
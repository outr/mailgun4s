package org.matthicks.mailgun

import fabric.rw.RW

sealed trait TrackingClicks

object TrackingClicks {
  private lazy val map = Map("default" -> Default, "yes" -> Yes, "no" -> No, "htmlonly" -> HTMLOnly)

  implicit val rw: RW[TrackingClicks] = RW.string(
    asString = _.getClass.getSimpleName.toLowerCase,
    fromString = s => map(s)
  )

  case object Default extends TrackingClicks
  case object Yes extends TrackingClicks
  case object No extends TrackingClicks
  case object HTMLOnly extends TrackingClicks
}
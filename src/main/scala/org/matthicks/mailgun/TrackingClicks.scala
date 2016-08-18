package org.matthicks.mailgun

sealed trait TrackingClicks

object TrackingClicks {
  case object Default extends TrackingClicks
  case object Yes extends TrackingClicks
  case object No extends TrackingClicks
  case object HTMLOnly extends TrackingClicks
}
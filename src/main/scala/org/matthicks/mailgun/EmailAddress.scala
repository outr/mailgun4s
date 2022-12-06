package org.matthicks.mailgun

import fabric.rw._

case class EmailAddress(address: String, name: Option[String] = None) {
  override def toString: String = name match {
    case Some(n) => s"$n <$address>"
    case _ => address
  }
}

object EmailAddress {
  implicit val rw: RW[EmailAddress] = RW.gen

  def apply(address: String, name: String): EmailAddress = EmailAddress(address, Option(name))
}
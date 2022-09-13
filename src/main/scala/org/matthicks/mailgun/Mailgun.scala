package org.matthicks.mailgun

import cats.effect.IO
import fabric.parse._
import fabric.rw._
import moduload.Moduload

import java.nio.charset.StandardCharsets
import java.util.Base64
import spice.http.client.HttpClient
import spice.http.content.Content
import spice.http.Headers

import spice.net._

import scala.util.{Failure, Success}

class Mailgun(domain: String, apiKey: String, region: Option[String] = None) {
  Moduload.load()

  private lazy val url: URL = URL(s"https://api.${region.map(r => s"$r.").getOrElse("")}mailgun.net/v3/$domain/messages")

  private lazy val encodedKey = new String(Base64.getEncoder.encode(s"api:$apiKey".getBytes(StandardCharsets.UTF_8)), "utf-8")
  private lazy val client = HttpClient
    .url(url)
    .post
    .header(Headers.Request.Authorization(s"Basic $encodedKey"))

  def send(message: Message): IO[MessageResponse] = {
    var content = Content.form

    def add(key: String, value: Any): Unit = {
      content = content.withString(key, value.toString)
    }

    add("from", message.from)
    add("subject", message.subject)
    message.to.foreach { to =>
      add("to", to)
    }
    message.cc.foreach { cc =>
      add("cc", cc)
    }
    message.bcc.foreach { bcc =>
      add("bcc", bcc)
    }
    message.tags.foreach { tag =>
      add("o:tag", tag)
    }
    message.campaignId.foreach { campaignId =>
      add("o:campaign", campaignId)
    }
    message.dkim.foreach { dkim =>
      add("o:dkim", if (dkim) "yes" else "no")
    }
    message.deliveryTime.foreach { deliveryTime =>
      add("o.deliverytime", deliveryTime)
    }
    if (message.testMode) {
      add("o:testmode", "yes")
    }
    message.tracking.foreach { tracking =>
      add("o:tracking", if (tracking) "yes" else "no")
    }
    message.trackingClicks match {
      case TrackingClicks.Default => // Nothing needs to be set
      case TrackingClicks.Yes => add("o:tracking-clicks", "yes")
      case TrackingClicks.No => add("o:tracking-clicks", "no")
      case TrackingClicks.HTMLOnly => add("o:tracking-clicks", "htmlonly")
    }
    message.trackingOpens.foreach { trackingOpens =>
      add("o:tracking-opens", if (trackingOpens) "yes" else "no")
    }
    if (message.requireTLS) {
      add("o:require-tls", "yes")
    }
    if (message.skipVerification) {
      add("o:skip-verification", "yes")
    }
    message.customHeaders.foreach {
      case (key, value) => add(s"h:$key", value)
    }
    message.customData.foreach {
      case (key, value) => add(s"v:$key", value)
    }
    message.template.foreach { template =>
      add("template", template)
    }
    message.text.foreach { text =>
      add("text", text)
    }
    message.html.foreach { text =>
      add("html", text)
    }
    message.attachments.foreach { attachment =>
      val contentType = Headers.`Content-Type`(ContentType.byFileName(attachment.file.getName))
      content = content.withFile("attachment", attachment.file.getName, attachment.file, Headers.empty.withHeader(contentType))
    }
    message.inline.foreach { inline =>
      val contentType = Headers.`Content-Type`(ContentType.byFileName(inline.file.getName))
      content = content.withFile("inline", inline.file.getName, inline.file, Headers.empty.withHeader(contentType))
    }

    client
      .content(content)
      .send()
      .map {
        case Success(response) =>
          val responseJson = response.content.map(_.asString).getOrElse("")
          if (responseJson.isEmpty) throw new RuntimeException(s"No content received in response for ${client.url}.")
          JsonParser.parse(responseJson).as[MessageResponse]
        case Failure(exception) => throw exception
      }
  }
}

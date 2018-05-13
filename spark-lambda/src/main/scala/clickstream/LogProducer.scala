package clickstream

import java.io.FileWriter

import config.Settings

import scala.util.Random


object LogProducer extends App {

  val wlc = Settings.WebLogGen

  println("adasdf")
  println(wlc.visitors)
  val Products = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/products.csv")).getLines().toArray

 val Referrers =  scala.io.Source.fromInputStream(getClass.getResourceAsStream("/referrers.csv")).getLines().toArray

  val Visitors = (0 to wlc.visitors).map("Visitor-"+ _)
  val Pages = (0 to wlc.pages).map("Pages-"+ _)

  val rnd = new Random()

  val filePath = wlc.filePath


  val fw = new FileWriter(filePath, true)

  val incrementTimeEvery = rnd.nextInt(wlc.records - 1)+1

  var timestamp = System.currentTimeMillis()

  var adjustedTimestamp = timestamp

  for(iteration <- 1 to wlc.records){
    adjustedTimestamp = adjustedTimestamp + ((System.currentTimeMillis() - timestamp) * wlc.timesmultipler)
    timestamp = System.currentTimeMillis()

    val action = iteration % (rnd.nextInt(200) + 1) match {
      case 0 => "purchase"
      case 1 => "add_to_cart"
      case _ => "page_view"
    }

    val referrer =  Referrers(rnd.nextInt(Referrers.length - 1))
    val prevPage = referrer match {
      case "Internal" => Pages(rnd.nextInt(Pages.length - 1))
      case _ => ""
    }
    val visitor = Visitors(rnd.nextInt(Visitors.length - 1))
    val page = Pages(rnd.nextInt(Pages.length - 1))
    val product = Products(rnd.nextInt(Products.length - 1))

    val line = s"$adjustedTimestamp\t$referrer\t$action\t$prevPage\t$visitor\t$page\t$product\n"
    fw.write(line)

    if (iteration % incrementTimeEvery == 0) {
      println(s"Sent $iteration messages!")
      val sleeping = rnd.nextInt(incrementTimeEvery * 60)
      println(s"Sleeping for $sleeping ms")
      Thread sleep sleeping
    }


  }
  fw.close()
}

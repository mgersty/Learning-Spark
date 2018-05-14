package batch

import java.lang.management.ManagementFactory

import org.apache.spark.{SparkContext, SparkConf}

object BatchJob {

  def main (args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("Lambda with Spark")

    val sc = new SparkContext(conf)

    println(sc)
  }

}

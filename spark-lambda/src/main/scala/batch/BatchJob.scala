package batch

import java.lang.management.ManagementFactory

import org.apache.spark.{SparkContext, SparkConf}

object BatchJob {

  def main (args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("Lambda with Spark")





    val sc = new SparkContext(conf)


    val sourceFile = "/Users/matthewgerstemeier/vagrant.virtual.machines/spark-kafka-cassandra-applying-lambda-architecture/vagrant/data.tsv"
    val input = sc.textFile(sourceFile)

    input.foreach(println)


  }

}

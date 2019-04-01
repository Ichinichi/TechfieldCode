import org.apache.spark._

object wordcount {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("oneCar").setMaster("local[*]")

    val sc: SparkContext = new SparkContext()

    val lines = sc.textFile( path = "/home/ichinichi/Downloads/shakespeare.txt")
    println(lines.take(1))

  }
}

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StructType, StructField, StringType}

class MovieScanner {



  def main(args: Array[String]): Unit = {


 //   val moviespath = "/home/ichinichi/Documents/movielens_march25_hw/data/movies.dat"
    val ratingspath = "/home/ichinichi/Documents/movielens_march25_hw/data/ratings.dat"
   // val userspath = "/home/ichinichi/Documents/movielens_march25_hw/data/users.dat"

    val spark = SparkSession.builder.master("local").appName("sparkToHive").getOrCreate()

  //  val moviesdf = spark.sparkContext.textFile(moviespath)
    val data_RDDStr = spark.sparkContext.textFile(ratingspath)
    val data_RDD = data_RDDStr.map(x => (x.split("::")(0).toLong,x.split("::")(1).toLong,x.split("::")(2).toLong,x.split("::")(3).toLong))
    // val usersdf = spark.sparkContext.textFile(userspath

    // The answer to Question 2. Counts the number of elements in RDD
    val movieCount = data_RDD.count()

    //the answer to question 3. filters returns rows that have the third column(ratings) equal to 1 start, then counts the columns
    val oneStarRatingCount = data_RDD.filter(line => line._3 == 1).count()

    //The answer to question 4. groups by the unique movie IDs then counts it
    val uniqueMoviesCount = data_RDD.groupBy(lines => lines._2).count()

    //The answer to question 5. unfinished
    val biggestCountUser = data_RDDStr.map(x => (x.split("::")(0).toLong))
    val biggestCountUser2 = biggestCountUser.countByValue()
    biggestCountUser.take(4).foreach(println)

  //  val teststr = data_RDDStr.map(x => x+1)
  //  val test = data_RDD.map(x => x+1)
    //look at data form
    data_RDDStr.take(4).foreach(println)
   // teststr.take(4).foreach(println)
    data_RDD.take(4).foreach(println)
   // test.take(4).foreach(println)

    //All the answers printed at the bottom.
    // Question 2 (1,000,209)
    System.out.println("2)The number of rated movies: "+movieCount)
    // Question 3 (56174)
    System.out.println("3)The number of one star ratings: "+oneStarRatingCount)
    // Question 4 (3706)
    System.out.println("4)The number of unique movies: "+uniqueMoviesCount)

    // Question 5 ()
    System.out.println("4)The user with the biggest rating count: ")
  }


}


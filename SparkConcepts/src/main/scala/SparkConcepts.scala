import org.apache.spark.SparkConf
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.streaming.{Seconds, StreamingContext}

class SparkConcepts {

  /*********************************First section Broadcasting*********************************
  Lets say for instance you are calculating the Damage Per Second(DPS) values for a variety of damage
    dealing classes in a Massive Multiplayer Online game within your nodes. The initial dataset you have of
    is actually the theoretical DPS values that you expect each class to have. The data you will eventually
    want is the difference between expected vs reality.
    Question 1) how would you declare this initial dataset to be a broadcast variable?
    Question 2) How would you call the broadast variable?*/

  val conf = new SparkConf().setMaster("local[*]")
  val ssc = new StreamingContext(conf, Seconds(1))
  val sc = ssc.sparkContext
  val expectedDPSArray = Array(7000, 9824, 8440)
  // Hint: Will use sparkContext

  val broadcastVar = //??????

    broadcastVar.//?????

  /*********************************Second section Accumulators*********************************
  All of the DPS arrays are being streamed into the nodes to be processed, however not all the information
  being fed into each node is perfect. Some are missing the a field in the data, some have numbers that
  dont make any sense(like zeros), and some are just completely blank. Finding out how often these records mess up will help point out
    potential issues that may be happening elsewhere in the pipeline.
    Question 3) Fill in how some of these could be done*/

  val zeroValueDPS = sc.accumulator(0, "Zero Value DPS")
  val missingFields = sc.accumulator(0, "Missing Fields")
  val blankLines = sc.accumulator(0, "Empty lines")

  sc.textFile("file:/home/ichinichi/spark-dev/data/DPSByClass.log", 4)
    .foreach { line =>

      if (/*????????*/) blankLines += 1
      else {
        val fields = line.split("\t")
       //note: theere should be 4 fields
        if (/*????????*/) missingFields += 1
        //note: the last field are the DPS numbers
        else if (/*????????*/) zeroValueDPS += 1
      }
    }

  /*********************************Third section Cache and Persist*********************************
  When transforming some of the RDDs, sometimes it is useful to have a specific RDD saved because multiple
    calculations will be derived from that parituclar RDD.
    Question 4) How do you save an RDD to memory*/

  val refinedRDD = sc.textFile("file:/home/ichinichi/spark-dev/data/DPSByClass.log")flatMap(line => line.split("\\W"))
  refinedRDD.///?????????

  /*********************************Third section Cache and Persist*********************************
  Window functions are useful for getting calcualtions about moving data, or in this case, being able to provide calculations
    from a subset of rows. Here, someone is trying to figure out the average DPS of all the players logged. Due to differences
    among the different classes, more specific averages by class may be more useful
    Question 5 & 6) What window function query would you use to yield this result?*/

  /*
    +-----------------+------------+--------+
    |    character_name     | class  | DPS  |
    +-----------------+------------+--------+
    | Ethram Auseil   | Caster     | 7500   |
    | Saliib Malamajar| Caster     | 6500   |
    | Facre Falow     | Caster     | 7000   |
    | Aeris Cloud     | Melee      | 7900   |
    | Mark Hewe       | Melee      | 8000   |
    | Abel Knox       | Melee      | 8200   |
    | Bullet          | Ranged     | 6000   |
    | Jack Mars       | Ranged     | 6500   |
    | Nana Momo       | Ranged     | 6500   |
    | Lili Aliti      | Ranged     | 6000   |
    +-----------------+------------+--------+*/

   /* select ???????????????????????? from DPS_scores;
    +-----------------+------------+--------+-----------+
    |    character_name     | class  | DPS  | AvgDPS  |
    +-----------------+------------+--------+-----------+
    | Ethram Auseil   | Caster     | 7500   | 7000     |
    | Saliib Malamajar| Caster     | 6500   | 7000     |
    | Facre Falow     | Caster     | 7000   | 7000     |
    | Aeris Cloud     | Melee      | 7900   | 7000     |
    | Mark Hewe       | Melee      | 8000   | 7000     |
    | Abel Knox       | Melee      | 8200   | 7000     |
    | Bullet          | Ranged     | 6000   | 7000     |
    | Jack Mars       | Ranged     | 6500   | 7000     |
    | Nana Momo       | Ranged     | 6500   | 7000     |
    | Lili Aliti      | Ranged     | 6000   | 7000     |
    +-----------------+------------+--------+-----------+

    select ???????????????????????? from DPS_scores;

    +-----------------+------------+--------+-----------+
    |    character_name     | class  | DPS  | AvgDPSbyCLass  |
     +-----------------+------------+--------+-----------+
    | Ethram Auseil   | Caster     | 7500   | 7000     |
    | Saliib Malamajar| Caster     | 6500   | 7000     |
    | Facre Falow     | Caster     | 7000   | 7000     |
    | Aeris Cloud     | Melee      | 7900   | 8000     |
    | Mark Hewe       | Melee      | 8000   | 8000     |
    | Abel Knox       | Melee      | 8200   | 8000     |
    | Bullet          | Ranged     | 6000   | 6250     |
    | Jack Mars       | Ranged     | 6500   | 6250     |
    | Nana Momo       | Ranged     | 6500   | 6250     |
    | Lili Aliti      | Ranged     | 6000   | 6250     |
    +-----------------+------------+--------+-----------+*/



  /*Q1 answer
   val broadcastVar = sc.broadcast(DPSArray)

  Q2 answer
   broadcastVar.value

  Q3 answer
  if (line.length() == 0) blankLines += 1
  else {
    val fields = line.split("\t")
    if (fields.length != 4) missingFields += 1
    else if (fields(3).toFloat == 0) zeroValueSales += 1
  }
  Q4) answer
  refinedRDD.cache()

  Q5)answer
  select character_name, class, DPS, avg(DPS) over() as AvgDPS from DPS_scores;
  select character_name, class, DPS, avg(DPS) over(partition by class) as AvgDPSbyClass from DPS_scores

  */

}


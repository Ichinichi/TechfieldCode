import org.apache.kafka.clients.producer.{KafkaProducer,ProducerConfig}
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties
import com.google.gson.Gson

import java.lang.ClassLoader

import com.film42.forecastioapi.ForecastIO

import scala.util.Success


object DarkSkyProducer {


  def main(args: Array[String]): Unit = {

    val topicName = "weather"
    val key = "hourlyWeather"
   // val value = "value-1"

    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    //getting the forecast from the API, this part connects using the key
    val forecastIO = ForecastIO("7ae9e85a8734ff8899a4dcd3d57a3f97", "us")

    //this identifies the location we are interested in, this one is specifically the Marietta Techfield
    val Success(forecast) = forecastIO.forecast("33.904383", "-84.454942")

    /* The original code that showed how to get information was too specific and gave a single value of a
    single json pair(forecast.hourly.summary). Using Gson turns the bigger section(forecast.hourly) of the json
    into a properly formed json itself. And thus can have a schema */
    val resultForecast = forecast.hourly
    val gson = new Gson()
    val resultForecastJson = gson.toJson(resultForecast)

    //just to check what the message is
  //  System.out.println("The the current forecast for techfield is "+ resultForecastJson )

    //since using this has limited calls, easch time this is run, it is limited to 4 messages over 40 seconds
    for (forecastNo: Int <- 0 to 3){

      for (counter: Int <- 1 to 5){
        System.out.println("just a timer "+ counter )
        Thread.sleep(2000)
      }
        System.out.println("printing weather JSON to broker" )
      val record = new ProducerRecord[String, String](topicName,key, resultForecastJson)

      producer.send(record)
    }


    producer.close()

    System.out.println("complete")

  }

}

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class sensorProducer {

    public static void main(String[]args) throws Exception{

        //defin topic name
        String topicName = "sensorProducerTopic2";

        //set kafka properties
        //***basic component***//
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //***paritioner component***//
        props.put("partitioner.class", "sensorPartitioner");
        props.put("speed.sensor.name", "TSS");

        //instance producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        //producing message

        for (int i = 0;i<10;i++)
            producer.send(new ProducerRecord<>(topicName, "SSP"+i, "500"+i));

        for (int i = 0; i<10;i++)
            producer.send(new ProducerRecord<>(topicName, "TSS", "500"+i));

        producer.close();

    }
}

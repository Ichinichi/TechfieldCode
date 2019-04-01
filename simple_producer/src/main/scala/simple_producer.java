import java.util.*;
import org.apache.kafka.clients.producer.*;

public class simple_producer {

    public static void main(String[] args) throws Exception{

        String topicName = "twoCar3";
        String key = "Key1";
        String value = "values-1";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer <>(props);

            ProducerRecord<String, String> record = new ProducerRecord<>(topicName,key,value);
            producer.send(record);

        producer.close();

                System.out.println("completed");
    }
}

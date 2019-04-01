import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class synchronousProducer {

    public static void main(String[] args) throws Exception {

        //defining topic
        String topicName = "synchronousProducerTopic";

        //defining key and value
        String key = "key1";
        String value = "value-1";

        //setting properties of kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //Instance the producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

        try{
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Message is sent to partion no"+metadata.partition() +"and offset"+metadata.offset());
            System.out.println("synchronous procer finished with sucess");

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Synchronous producer has failed");
        } finally{
            producer.close();
        }
    }

}

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class asynchronousProducer {

    public static void main(String[] args) throws Exception{

        String topicName = "asynchronousProducerTopic";
        String key = "key1";
        String value = "value-1";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>(topicName,key,value);
        producer.send(record, new MyProducerCallback());

        producer.close();

        System.out.println("complete");
    }
}

class MyProducerCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null)
            System.out.println("Asynchronous producer failed with an exception");

        else
            System.out.println("Asynchronous producer call success");
    }
}

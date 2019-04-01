import java.util.*;
import java.io.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class supplierConsumer{

    public static void main(String[] args) throws Exception{

        String topicName = "SupplierTopic";
        String groupName = "SupplierTopicGroup";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        InputStream input = null;
        KafkaConsumer<String, customSupplier> consumer = null;

        try {
            // input = new FileInputStream("SupplierConsumer.properties");
            // props.load(input);
            consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList(topicName));

            while (true){

                ConsumerRecords<String, customSupplier> records = consumer.poll(100);

                for (ConsumerRecord<String, customSupplier> record : records){
                    System.out.println("customSupplier id= " + String.valueOf(record.value().getID()) + " customSupplier  Name = " + record.value().getName() + " customSupplier Start Date = " + record.value().getStartDate().toString());
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            input.close();
            consumer.close();
        }
    }
}
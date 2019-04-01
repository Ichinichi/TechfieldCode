import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.kafka.clients.producer.*;
public class supplierProducer {

    public static void main(String[] args) throws Exception{

        String topicName = "SupplierTopic";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "supplierSerializer");

        Producer<String, customSupplier> producer = new KafkaProducer <>(props);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        customSupplier sp1 = new customSupplier(101,"Xyz Pvt Ltd.",df.parse("2016-04-01"));
        customSupplier sp2 = new customSupplier(102,"Abc Pvt Ltd.",df.parse("2012-01-01"));

        producer.send(new ProducerRecord<String, customSupplier>(topicName,"SUP",sp1)).get();
        producer.send(new ProducerRecord<String, customSupplier>(topicName,"SUP",sp2)).get();

        System.out.println("SupplierProducer Completed.");
        producer.close();

    }
}
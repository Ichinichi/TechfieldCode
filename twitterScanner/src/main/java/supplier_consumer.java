import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class supplier_consumer {
    public static void main(String[] args) throws Exception{

        //define topic name
        String topicName = "SupplierTopic";

        // define consumer group
        String groupName = "SupplierTopicGroup";

        //defince consumer properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093");
        props.put("group,id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "SupplierDeserializer");

        InputStream input = null;

        //instanciate consumer
        KafkaConsumer<String, Supplier> consumer = null;

        }
    }

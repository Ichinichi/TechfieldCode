import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class SupplierDeserializer implements Deserializer<customSupplier> {
    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Nothing to configure
    }

    @Override
    public customSupplier deserialize(String topic, byte[] data) {

        try {
            if (data == null){
                System.out.println("Null recieved at deserialize");
                return null;
            }
            ByteBuffer buf = ByteBuffer.wrap(data);
            int id = buf.getInt();

            int sizeOfName = buf.getInt();
            byte[] nameBytes = new byte[sizeOfName];
            buf.get(nameBytes);
            String deserializedName = new String(nameBytes, encoding);

            int sizeOfDate = buf.getInt();
            byte[] dateBytes = new byte[sizeOfDate];
            buf.get(dateBytes);
            String dateString = new String(dateBytes,encoding);

            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

            return new customSupplier(id,deserializedName,df.parse(dateString));



        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to customSupplier");
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}
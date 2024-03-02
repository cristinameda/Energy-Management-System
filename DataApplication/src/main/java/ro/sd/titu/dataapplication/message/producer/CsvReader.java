package ro.sd.titu.dataapplication.message.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import ro.sd.titu.dataapplication.entity.Device;
import ro.sd.titu.dataapplication.entity.Measurement;
import ro.sd.titu.dataapplication.repository.DeviceRepository;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class CsvReader {
    private static final String QUEUE_NAME = "kit";
    private final DeviceRepository deviceRepository;
    private final ConnectionFactory factory;
    private final Iterable<CSVRecord> records;
    private final Iterator<CSVRecord> recordIterator;

    public CsvReader(DeviceRepository deviceRepository) throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this.deviceRepository = deviceRepository;
        this.factory = new ConnectionFactory();
        this.factory.setUri("amqps://fyyzodyx:IcFrBgJ7f6FP64dvMPcAPSA_dATS9RBR@fish.rmq.cloudamqp.com/fyyzodyx");

        // load CSV records
        try (Reader reader = new FileReader(System.getProperty("user.dir") + "/src" + "/main" + "/resources" + "/sensor.csv")) {
            this.records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);
            this.recordIterator = this.records.iterator();
        }
    }

    public void startSendingMessages() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::sendMessage, 0, 10, TimeUnit.SECONDS);
    }

    private void sendMessage() {
        if (recordIterator.hasNext()) {
            CSVRecord record = recordIterator.next();

            try (Connection connection = factory.newConnection()) {
                try (Channel channel = connection.createChannel()) {
                    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

                    // convert CSV record to an object
                    Measurement measurement = createMeasurementObject(record);

                    // convert object to JSON
                    String jsonMessage = convertObjectToJson(measurement);

                    // publish the message to the queue
                    channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());
                    System.out.println(" [x] Sent: " + jsonMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Measurement createMeasurementObject(CSVRecord record) {
        Optional<Device> device = deviceRepository.findById(BigInteger.valueOf(2));
        BigInteger deviceId = device.map(Device::getId).orElse(null);
        LocalDateTime timestamp = LocalDateTime.now();
        float measurementValue = Float.parseFloat(record.get(0));

        return new Measurement(timestamp, deviceId, measurementValue);
    }

    private String convertObjectToJson(Measurement measurement) {
        return String.format("{\"deviceId\":\"%s\",\"timestamp\":%s,\"value\":\"%f\"}",
                measurement.getDeviceId(), measurement.getTimestamp().toString(), measurement.getValue());
    }
}

package ro.sd.titu.monitoringapplication.message.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.sd.titu.monitoringapplication.dto.MeasurementDTO;
import ro.sd.titu.monitoringapplication.entity.Measurement;
import ro.sd.titu.monitoringapplication.service.MonitorService;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Component
public class MessageConsumer {

    private static final String QUEUE_NAME = "kit";
    private final MonitorService monitorService;

    public MessageConsumer(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @Scheduled(fixedRate = 1000)
    public void consume() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://fyyzodyx:IcFrBgJ7f6FP64dvMPcAPSA_dATS9RBR@fish.rmq.cloudamqp.com/fyyzodyx");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // create a consumer
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received: " + message);

                MeasurementDTO measurementDTO = parseJson(message);
                Measurement measurement = new Measurement(
                        measurementDTO.getTimestamp(),
                        measurementDTO.getDeviceId(),
                        measurementDTO.getValue()
                );
                monitorService.save(measurement);
            };

            // start consuming messages
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

            // keep the consumer running
            Thread.sleep(5000);
        }
    }

    private MeasurementDTO parseJson(String json) {
        // remove surrounding curly braces
        json = json.substring(1, json.length() - 1);

        // split the JSON string into key-value pairs
        String[] parts = json.split(",");

        // parse deviceId
        BigInteger deviceId = new BigInteger(parts[0].split(":")[1].replace("\"", "").trim());

        // parse timestamp
        String[] timestampStrArray = parts[1].split(":");
        String timestampStr = timestampStrArray[1] + ":" + timestampStrArray[2] + ":" + timestampStrArray[3];
        LocalDateTime timestamp = LocalDateTime.parse(timestampStr);

        // parse value
        String valueStr = parts[2].split(":")[1].replace("\"", "").trim();
        float value = Float.parseFloat(valueStr);

        return new MeasurementDTO(timestamp, deviceId, value);
    }
}

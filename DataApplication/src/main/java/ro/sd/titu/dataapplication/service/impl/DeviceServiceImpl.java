package ro.sd.titu.dataapplication.service.impl;

import org.springframework.stereotype.Service;
import ro.sd.titu.dataapplication.message.producer.CsvReader;
import ro.sd.titu.dataapplication.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final CsvReader csvReader;

    public DeviceServiceImpl(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    @Override
    public void transmit() {
        csvReader.startSendingMessages();
    }
}

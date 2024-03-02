package ro.sd.titu.devicemanagementapplication.service;

import ro.sd.titu.devicemanagementapplication.dto.DeviceDTO;

import java.math.BigInteger;
import java.util.List;

public interface DeviceService {
    BigInteger save(DeviceDTO deviceDTO);

    BigInteger update(DeviceDTO deviceDTO);

    void deleteById(BigInteger id);

    List<DeviceDTO> findAll();

    DeviceDTO findById(BigInteger id);

    List<DeviceDTO> findAllByUserId(BigInteger userId);

    void deleteAllByUserId(BigInteger userId);
}

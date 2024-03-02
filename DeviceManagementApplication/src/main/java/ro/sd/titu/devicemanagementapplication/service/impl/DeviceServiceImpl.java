package ro.sd.titu.devicemanagementapplication.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ro.sd.titu.devicemanagementapplication.dto.DeviceDTO;
import ro.sd.titu.devicemanagementapplication.dto.builder.DeviceBuilder;
import ro.sd.titu.devicemanagementapplication.entity.Device;
import ro.sd.titu.devicemanagementapplication.repository.DeviceRepository;
import ro.sd.titu.devicemanagementapplication.service.DeviceService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
    private final DeviceRepository deviceRepository;
    private final WebClient webClient;

    public DeviceServiceImpl(DeviceRepository deviceRepository, WebClient webClient) {
        this.deviceRepository = deviceRepository;
        this.webClient = webClient;
    }

    @Override
    public BigInteger save(DeviceDTO deviceDTO) {
        // check if user exists (userId is valid) calling the findById endpoint from user management microservice
        findUserById(deviceDTO.getUserId());

        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.info("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    @Override
    public BigInteger update(DeviceDTO deviceDTO) {
        Optional<Device> optionalDevice = deviceRepository.findById(deviceDTO.getId());
        if (optionalDevice.isEmpty()) {
            LOGGER.error("Device to be updated with id {} was not found in db", deviceDTO.getId());
            throw new ResourceNotFoundException("Device with id " + deviceDTO.getId() + " does not exist");
        }
        Device oldDevice = optionalDevice.get();
        Device updatedDevice = DeviceBuilder.toEntity(deviceDTO);
        updatedDevice.setId(deviceDTO.getId());
        if (deviceDTO.getUserId() == null) {
            updatedDevice.setUserId(oldDevice.getUserId());
        }
        updatedDevice = deviceRepository.save(updatedDevice);
        LOGGER.info("Device with id {} was updated", updatedDevice.getId());
        return updatedDevice.getId();
    }

    @Override
    public void deleteById(BigInteger id) {
        if (!deviceRepository.existsById(id)) {
            LOGGER.error("Device to be deleted with id {} was not found in db", id);
            throw new ResourceNotFoundException("Device with id " + id + " does not exist");
        }
        deviceRepository.deleteById(id);
        LOGGER.info("Device with id {} was deleted from db", id);
    }

    @Override
    public List<DeviceDTO> findAll() {
        List<Device> userList = deviceRepository.findAll();
        return userList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDTO findById(BigInteger id) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isEmpty()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException("Device with id " + id + " does not exist");
        }
        return DeviceBuilder.toDeviceDTO(optionalDevice.get());
    }

    @Override
    public List<DeviceDTO> findAllByUserId(BigInteger userId) {
        findUserById(userId);
        List<Device> userList = deviceRepository.findAllByUserId(userId);
        return userList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllByUserId(BigInteger userId) {
        deviceRepository.deleteAllByUserId(userId);
        LOGGER.info("Devices with user id {} were deleted from db", userId);
    }

    private void findUserById(BigInteger id) {
        webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(httpStatusCode ->
                                !httpStatusCode.is2xxSuccessful(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("User with id " + id + " was not found in db"))
                )
                .bodyToMono(String.class)
                .block();
    }
}

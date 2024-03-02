package ro.sd.titu.devicemanagementapplication.dto.builder;

import ro.sd.titu.devicemanagementapplication.dto.DeviceDTO;
import ro.sd.titu.devicemanagementapplication.entity.Device;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMaxConsumption(), device.getUserId());
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getDescription(), deviceDTO.getAddress(), deviceDTO.getMaxConsumption(), deviceDTO.getUserId());
    }
}

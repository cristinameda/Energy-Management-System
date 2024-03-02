package ro.sd.titu.usermanagementapplication.dto.builder;

import ro.sd.titu.usermanagementapplication.controller.response.UserResponse;
import ro.sd.titu.usermanagementapplication.dto.UserDTO;
import ro.sd.titu.usermanagementapplication.entity.User;

public class UserBuilder {

    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getDob(), user.getRole());
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getName(), userDTO.getAddress(), userDTO.getDob(), userDTO.getRole());
    }

    public static UserResponse toUserResponse(UserDTO userDTO) {
        return new UserResponse(userDTO.getId(), userDTO.getUsername(), userDTO.getName(), userDTO.getAddress(), userDTO.getDob(), userDTO.getRole().name());
    }
}

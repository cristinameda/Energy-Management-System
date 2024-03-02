package ro.sd.titu.usermanagementapplication.service;

import ro.sd.titu.usermanagementapplication.dto.UserDTO;
import ro.sd.titu.usermanagementapplication.entity.Role;

import java.math.BigInteger;
import java.util.List;

public interface UserService {
    BigInteger save(UserDTO userDTO);

    BigInteger update(UserDTO userDTO);

    void deleteById(BigInteger id);

    List<UserDTO> findAll();

    UserDTO findById(BigInteger id);

    UserDTO findByUsername(String username);

    List<UserDTO> findAllByRole(Role role);
}

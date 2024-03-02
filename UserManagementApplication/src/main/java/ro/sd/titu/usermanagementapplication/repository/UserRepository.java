package ro.sd.titu.usermanagementapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.titu.usermanagementapplication.entity.Role;
import ro.sd.titu.usermanagementapplication.entity.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);
}

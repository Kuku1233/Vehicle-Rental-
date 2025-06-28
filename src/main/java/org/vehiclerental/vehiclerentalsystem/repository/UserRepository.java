package org.vehiclerental.vehiclerentalsystem.repository;

import org.springframework.stereotype.Repository;
import org.vehiclerental.vehiclerentalsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

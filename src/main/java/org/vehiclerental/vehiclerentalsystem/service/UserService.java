package org.vehiclerental.vehiclerentalsystem.service;

import org.vehiclerental.vehiclerentalsystem.model.User;

public interface UserService {
    User findByUsername(String username);
    void saveUser(User user);
}


package com.uep.wap.eshop.remotech.service;

import com.uep.wap.eshop.remotech.entity.User;
//import com.uep.wap.eshop.remotech.entity.UserRole;
import com.uep.wap.eshop.remotech.repository.UserRepository;
//import com.uep.wap.eshop.remotech.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    //private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        //this.userRoleRepository = userRoleRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // public Optional<User> updateUserRole(Long userId, String roleName) {
    //     // Find the user
    //     Optional<User> userOptional = userRepository.findById(userId);
    //     if (userOptional.isEmpty()) {
    //         return Optional.empty();
    //     }

    //     // Find the role by authority name
    //     UserRole role = userRoleRepository.findByAuthority(roleName);
    //     if (role == null) {
    //         throw new RuntimeException("Role not found with name: " + roleName);
    //     }

    //     // Update the user's role
    //     User user = userOptional.get();
    //     user.setUserRole(role);
        
    //     return Optional.of(userRepository.save(user));
    // }
} 
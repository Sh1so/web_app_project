package com.uep.wap.eshop.remotech.service;

import com.uep.wap.eshop.remotech.entity.User;
import com.uep.wap.eshop.remotech.entity.Cart;
//import com.uep.wap.eshop.remotech.entity.UserRole;
import com.uep.wap.eshop.remotech.repository.UserRepository;
//import com.uep.wap.eshop.remotech.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uep.wap.eshop.remotech.security.SecurityConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CartService cartService){
        this.userRepository = userRepository;
        this.cartService = cartService;
        //this.userRoleRepository = userRoleRepository;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void registerNewUser(User user, String rawPassword) {
        String encodedPassword = encodePassword(rawPassword);
        user.setPassword(encodedPassword);
        // save user to repository here...
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(User user) {
        // Create a new cart for the user
        Cart cart = new Cart(user);
        user.setCart(cart);
        
        // Save the user (cart will be saved automatically due to cascade)
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Preserve timestamps and update updatedAt
                    user.setId(id);
                    user.setCreatedAt(existingUser.getCreatedAt());
                    user.setUpdatedAt(LocalDateTime.now());
                    
                    // Preserve relationships
                    user.setOrders(existingUser.getOrders());
                    user.setCart(existingUser.getCart());
                    
                    return userRepository.save(user);
                });
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
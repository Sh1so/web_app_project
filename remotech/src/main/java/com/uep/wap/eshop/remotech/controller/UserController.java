package com.uep.wap.eshop.remotech.controller;

import com.uep.wap.eshop.remotech.entity.User;
import com.uep.wap.eshop.remotech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        // Opcjonalnie: wyczyść hasło przed zwróceniem
        if (user != null) {
            user.setPassword(null); // Nie wysyłaj hasła do frontendu!
        }

        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    // @PutMapping("/{id}/role")
    // public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestParam String role) {
    //     try {
    //         return userService.updateUserRole(id, role)
    //                 .map(ResponseEntity::ok)
    //                 .orElse(ResponseEntity.notFound().build());
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.badRequest().build();
    //     }
    // }
} 
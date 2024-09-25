package com.edvinas.user.controller;

import com.edvinas.user.VO.ResponseTemplateVO;
import com.edvinas.user.entity.User;
import com.edvinas.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user) {
        log.info("Inside saveUser of UserController");
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId){
        log.info("Inside getUserWithDepartment of UserController");
        return userService.getUserWithDepartment(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        log.info("Inside deleteUser of UserController");
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User with ID " + userId + " deleted successfully.");
    }

//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
//        log.info("Inside updateUser of UserController");
//        return userService.updateUser(userId, user);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        log.info("Inside updateUser of UserController");
        userService.updateUser(userId, user);
        return ResponseEntity.ok("User with ID " + userId + " updated successfully.");
    }


}

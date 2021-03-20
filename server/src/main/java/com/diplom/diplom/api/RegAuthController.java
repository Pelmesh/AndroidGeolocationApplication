package com.diplom.diplom.api;

import com.diplom.diplom.model.User;
import com.diplom.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class RegAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("registration")
    public Object registration(@RequestBody User user) {
        User userRepeat = userService.findByName(user.getUsername());
        if (userRepeat != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        userRepeat = userService.findByEmail(user.getEmail());
        if (userRepeat != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("login")
    public Object login(@RequestBody User user) {
        User userRepeat = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userRepeat == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return userRepeat;
    }

}

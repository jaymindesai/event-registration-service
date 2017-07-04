package com.services.application;

import com.services.domain.user.UserDto;
import com.services.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("user")
    public UserDto getUser(@RequestParam(value = "email", required = true) String email){
        return userService.find(email);
    }

    private void isUserRegistered(){
        userService.checkIfUserRegistered();
    }
}

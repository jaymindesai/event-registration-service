package com.services.application;

import com.services.domain.user.UserDto;
import com.services.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable Integer id){
        return userService.find(id);
    }

}

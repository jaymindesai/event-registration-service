package com.ers.application;

import com.ers.domain.user.UserDto;
import com.ers.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.ACCEPTED;

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

    @PostMapping("")
    @ResponseStatus(ACCEPTED)
    public String addUser(@Valid @RequestBody UserDto user, BindingResult result){
        return userService.addUser(user, result) ? "User Registered!" : "Failed To Register User!";
    }

    @DeleteMapping("{id}")
    @ResponseStatus(ACCEPTED)
    public void deleteUser(@PathVariable Integer id){
        userService.delete(id);
    }
}

package com.services.application;

import com.services.domain.user.UserDto;
import com.services.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

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
    public void addUser(@Valid @RequestBody UserDto user, BindingResult result){
        userService.addUser(user, result);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(ACCEPTED)
    public void deleteUser(@PathVariable Integer id){
        userService.delete(id);
    }
}

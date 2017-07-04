package com.services.domain.user;

import com.services.domain.user.converters.UserConverter;
import com.services.infrastructure.UserRepository;
import com.services.exceptions.UnregisteredUserException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private HttpServletRequest request;
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    @Autowired
    public UserService(HttpServletRequest request, UserConverter userConverter, UserRepository userRepository){
        this.request = request;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    public void checkIfUserRegistered(){
        User user = userRepository.findByEmail(request.getHeader("email"));
        if(user == null){
            throw new UnregisteredUserException("User not registered");
        }
    }

    public UserDto find(String email){
        return userConverter.convertToDto(userRepository.findByEmail(email));
    }
}

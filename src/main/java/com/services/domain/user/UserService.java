package com.services.domain.user;

import com.services.domain.user.converters.UserConverter;
import com.services.application.handler.exceptions.UnregisteredUserException;
import com.services.infrastructure.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;

@Service
public class UserService {

    private HttpServletRequest request;
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    @Autowired
    public UserService(HttpServletRequest request, UserConverter userConverter, UserRepository userRepository) {
        this.request = request;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto find(Integer id) {
        return userConverter.convertToDto(userRepository.findOne(id));
    }

    @Transactional
    public Boolean addUser(UserDto user, BindingResult result) {
        validateUser(result);
        return userRepository.save(userConverter.convertToUser(user)) != null;
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    public User checkIfUserRegistered() {
        User user = userRepository.findByEmail(request.getHeader("email"));
        if (user == null) {
            throw new UnregisteredUserException("User not registered with the system.");
        }
        return user;
    }

    private void validateUser(BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(createErrorMessage(result));
        }
    }

    private String createErrorMessage(BindingResult result) {
        StringBuilder errors = new StringBuilder();
        result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .forEach(error -> errors.append(error).append(", "));
        return errors.substring(0, errors.length() - 2);
    }
}

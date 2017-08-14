package com.ers.domain.user;

import com.ers.application.handler.exceptions.NotFoundException;
import com.ers.domain.user.dto.UserDto;
import com.ers.infrastructure.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;
import java.util.Optional;

import static com.ers.domain.user.converters.UserConverter.convertToDto;
import static com.ers.domain.user.converters.UserConverter.convertToUser;

@Service
public class UserService {

    private HttpServletRequest request;
    private final UserRepository userRepository;

    @Autowired
    public UserService(HttpServletRequest request, UserRepository userRepository) {
        this.request = request;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto find(int id) {
        return convertToDto(Optional.ofNullable(userRepository.findOne(id))
                .orElseThrow(() -> new NotFoundException("User not registered with the system!")));
    }

    @Transactional
    public User user(int id) {
        return Optional.ofNullable(userRepository.findOne(id))
                .orElseThrow(() -> new NotFoundException("User not registered with the system!"));
    }

    @Transactional
    public Boolean addUser(UserDto user, BindingResult result) {
        validateUser(result);
        return userRepository.save(convertToUser(user)) != null;
    }

    @Transactional
    public void delete(int id) {
        userRepository.delete(id);
    }

    public User checkIfUserRegistered() {
        return userRepository.findByEmail(request.getHeader("email"))
                .orElseThrow(() -> new NotFoundException("User not registered with the system!"));
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

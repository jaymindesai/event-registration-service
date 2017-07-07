package com.services.application;

import com.services.domain.registration.RegistrationDto;
import com.services.domain.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @GetMapping("")
    public List<RegistrationDto> getRegistrations(){
        return registrationService.getRegistrations();
    }

    @RequestMapping("event/{eventCode}/slot/{slotCode}/user/{id}")
    @ResponseStatus(ACCEPTED)
    public String registerForEvent(@PathVariable String eventCode, @PathVariable String slotCode, @PathVariable int id){
        registrationService.register(eventCode, slotCode, id);
        return "Registration Successful!";
    }
}

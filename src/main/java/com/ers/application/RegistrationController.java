package com.ers.application;

import com.ers.domain.registration.RegistrationDto;
import com.ers.domain.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("")
    public List<RegistrationDto> getRegistrations() {
        return registrationService.getRegistrations();
    }

    /*
     * This end point is just for administrative purpose and should not be used directly.
     */
    @RequestMapping("event/{eventCode}/slot/{slotCode}/user/{id}")
    @ResponseStatus(ACCEPTED)
    public String registerForEvent(@PathVariable String eventCode, @PathVariable String slotCode, @PathVariable int id) {
        registrationService.register(eventCode, slotCode, id);
        return "Registration Successful!";
    }

    /*
     * This end point handles the registration confirmation link send via email.
     */
    @RequestMapping("registration")
    @ResponseStatus(ACCEPTED)
    public String register(HttpServletRequest request) {
        String eventCode = (String) request.getAttribute("eventCode");
        String slotCode = (String) request.getAttribute("slotCode");
        Integer id = Integer.valueOf((String) request.getAttribute("id"));
        registrationService.register(eventCode, slotCode, id);
        return "Registration Successful!";
    }
}

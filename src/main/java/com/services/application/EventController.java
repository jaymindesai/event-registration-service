package com.services.application;

import com.services.domain.event.EventDto;
import com.services.domain.event.EventService;
import com.services.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Slf4j
@RestController
@RequestMapping("events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    /*
     * This endpoint provides list of events only to the pre-registered users of this system.
     * For that a unique email_id of the registered user must be present in the request header.
     */
    @GetMapping("")
    public List<EventDto> getEvents(){
        userService.checkIfUserRegistered();
        return eventService.getEvents();
    }

    @GetMapping("{code}")
    public EventDto getEvent(@PathVariable String code) {
        return eventService.findByCode(code);
    }

    @PutMapping("{code}/timeSlots/{slot}/capacity/{capacity}")
    public EventDto updateSlotCapacity(@PathVariable String code,
                                       @PathVariable String slot,
                                       @PathVariable int capacity) {
        return eventService.updateSlotCapacity(code, slot, capacity);
    }

    @DeleteMapping("events/{code}")
    @ResponseStatus(ACCEPTED)
    public void deleteEvent(@PathVariable String code) {
        eventService.deleteByCode(code);
    }
}

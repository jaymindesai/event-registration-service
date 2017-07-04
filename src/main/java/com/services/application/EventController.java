package com.services.application;

import com.services.domain.event.EventDto;
import com.services.domain.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Slf4j
@RestController
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("events")
    public List<EventDto> getEvents(){
        return eventService.getEvents();
    }

    @GetMapping("event/{code}")
    public EventDto getEvent(@PathVariable String code) {
        return eventService.findByCode(code);
    }

    @PutMapping("event/{code}/timeSlots/{slot}/capacity/{capacity}")
    public EventDto updateSlotCapacity(@PathVariable String code,
                                       @PathVariable String slot,
                                       @PathVariable int capacity) {
        return eventService.updateSlotCapacity(code, slot, capacity);
    }

    @DeleteMapping("event/{code}")
    @ResponseStatus(ACCEPTED)
    public void deleteEvent(@PathVariable String code) {
        eventService.deleteByCode(code);
    }
}

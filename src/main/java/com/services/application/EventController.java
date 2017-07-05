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
@RequestMapping("events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public List<EventDto> getEvents(){
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

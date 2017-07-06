package com.services.domain.event;

import com.services.application.handler.exceptions.NotFoundException;
import com.services.domain.event.converters.EventConverter;
import com.services.application.handler.exceptions.InvalidEventException;
import com.services.infrastructure.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
@Service
public class EventService {

    private final EventConverter eventConverter;
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventConverter eventConverter, EventRepository eventRepository) {
        this.eventConverter = eventConverter;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventDto findByCode(String eventCode) {
        return eventConverter.convertToDto(isValidEvent(eventCode));
    }

    @Transactional
    public EventDto updateSlotCapacity(String eventCode, String slotCode, int capacity) {
        Event event = isValidEvent(eventCode);
        event.getVenue().getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getSlotCode().equals(slotCode))
                .forEach(timeSlot -> timeSlot.setCapacity(capacity));
        return eventConverter.convertToDto(eventRepository.save(event));
    }

    @Transactional
    public List<EventDto> getEvents() {
        List<EventDto> events = stream(eventRepository.findAll().spliterator(), false)
                .map(eventConverter::convertToDto)
                .collect(toList());
        if(events.isEmpty()){
            throw new NotFoundException("No Events Found!");
        }
        return events;
    }

    @Transactional
    public void deleteByCode(String eventCode) {
        Event event = isValidEvent(eventCode);
        eventRepository.deleteByCode(event.getCode());
    }

    @Transactional
    public Event event(String eventCode) {
        return isValidEvent(eventCode);
    }

    public Event isValidEvent(String eventCode) {
        return eventRepository.findByCode(eventCode)
                .orElseThrow(() -> new InvalidEventException("Invalid event eventCode - " + eventCode + ", no such event found!"));
    }
}

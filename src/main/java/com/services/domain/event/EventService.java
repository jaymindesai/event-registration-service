package com.services.domain.event;

import com.services.domain.event.converters.EventConverter;
import com.services.application.handler.exceptions.InvalidEventException;
import com.services.infrastructure.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public EventDto findByCode(String code) {
        return eventConverter.convertToDto(isValidEvent(code));
    }

    @Transactional
    public EventDto updateSlotCapacity(String code, String slotCode, int capacity) {
        Event event = isValidEvent(code);
        event.getVenue().getTimeSlots().stream()
                .filter(slot -> slot.getSlotCode().equals(slotCode))
                .forEach(slot -> slot.setCapacity(capacity));
        return eventConverter.convertToDto(eventRepository.save(event));
    }

    @Transactional
    public List<EventDto> getEvents() {
        return stream(eventRepository.findAll().spliterator(), false)
                .map(eventConverter::convertToDto)
                .collect(toList());
    }

    @Transactional
    public void deleteByCode(String code) {
        Event event = isValidEvent(code);
        eventRepository.deleteByCode(event.getCode());
    }

    public Event isValidEvent(String code) {
        Event event = eventRepository.findByCode(code);
        if (event == null) {
            throw new InvalidEventException("Invalid event code - " + code + ", no such event found.");
        }
        return event;
    }
}

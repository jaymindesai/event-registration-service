package com.ers.domain.event;

import com.ers.domain.event.converters.EventConverter;
import com.ers.handler.exceptions.NotFoundException;
import com.ers.infrastructure.EventRepository;
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
    public EventDto findByCode(String eventCode) {
        return eventConverter.convertToDto(isValidEvent(eventCode));
    }

    @Transactional
    public EventDto updateSlotCapacity(String eventCode, String slotCode, int capacity) {
        Event event = isValidEvent(eventCode);
        event.getVenue().getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getSlotCode().equals(slotCode))
                .forEach(timeSlot -> timeSlot.setCapacity(capacity));
        Event save = eventRepository.save(event);
        return eventConverter.convertToDto(save);
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
                .orElseThrow(() -> new NotFoundException("Invalid event eventCode - " + eventCode + ", no such event found!"));
    }
}

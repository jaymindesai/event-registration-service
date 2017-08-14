package com.ers.domain.event;

import com.ers.application.handler.exceptions.NotFoundException;
import com.ers.domain.event.converters.EventConverter;
import com.ers.domain.event.dto.EventDto;
import com.ers.infrastructure.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static com.ers.domain.event.converters.EventConverter.convertToDto;
import static java.util.stream.Collectors.toList;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventDto findByCode(String eventCode) {
        return convertToDto(isValidEvent(eventCode));
    }

    @Transactional
    public EventDto updateSlotCapacity(String eventCode, String slotCode, int capacity) {
        Event event = isValidEvent(eventCode);
        event.getVenue().getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getSlotCode().equals(slotCode))
                .forEach(timeSlot -> timeSlot.setCapacity(capacity));
        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    @Transactional
    public List<EventDto> getEvents() {
        List<EventDto> events = StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                .map(EventConverter::convertToDto)
                .collect(toList());
        if (events.isEmpty()) {
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

    private Event isValidEvent(String eventCode) {
        return eventRepository.findByCode(eventCode)
                .orElseThrow(() -> new NotFoundException("Invalid event eventCode - " + eventCode + ", no such event found!"));
    }
}

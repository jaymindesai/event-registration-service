package com.ers.domain.registration;

import com.ers.domain.event.Event;
import com.ers.domain.event.EventService;
import com.ers.domain.event.TimeSlot;
import com.ers.domain.registration.converters.RegistrationConverter;
import com.ers.domain.user.User;
import com.ers.domain.user.UserService;
import com.ers.handler.exceptions.EventRegistrationException;
import com.ers.handler.exceptions.NotFoundException;
import com.ers.infrastructure.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationConverter registrationConverter;
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository,
                               RegistrationConverter registrationConverter,
                               EventService eventService,
                               UserService userService) {
        this.registrationRepository = registrationRepository;
        this.registrationConverter = registrationConverter;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Transactional
    public List<RegistrationDto> getRegistrations() {
        List<RegistrationDto> registrations = stream(registrationRepository.findAll().spliterator(), false)
                .map(registrationConverter::convertToDto)
                .collect(toList());
        if(registrations.isEmpty()){
            throw new NotFoundException("No Registraions Found!");
        }
        return registrations;
    }

    @Transactional
    public boolean register(String eventCode, String slotCode, int id) {
        Event event = eventService.event(eventCode);
        User user = userService.user(id);
        checkIfRegistrationAlreadyExists(event, user);
        TimeSlot timeSlot = getSlot(event, slotCode);
        if (timeSlot.isFull()){
            throw new EventRegistrationException("Time slot completely booked!");
        }
        eventService.updateSlotCapacity(eventCode, slotCode, timeSlot.decrementCapacity());
        return registrationRepository.save(Registration.builder().event(event).user(user).timeSlot(timeSlot).build()) != null;
    }

    private void checkIfRegistrationAlreadyExists(Event event, User user) {
        if (registrationRepository.findByEventAndUser(event, user).isPresent()) {
            throw new EventRegistrationException("Registration for event " + event.getCode() + " already exists!");
        }
    }

    private TimeSlot getSlot(Event event, String slotCode) {
        return event.getVenue().getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getSlotCode().equals(slotCode))
                .findFirst()
                .get();
    }
}

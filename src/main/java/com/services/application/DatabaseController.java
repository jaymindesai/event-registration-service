package com.services.application;

import com.services.domain.event.Venue;
import com.services.domain.user.User;
import com.services.infrastructure.EventRepository;
import com.services.infrastructure.RegistrationRepository;
import com.services.infrastructure.UserRepository;
import com.services.domain.registration.Registration;
import com.services.domain.event.Event;
import com.services.domain.event.TimeSlot;
import com.services.domain.user.Address;
import com.services.domain.user.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.services.domain.City.AHMEDABAD;
import static com.services.domain.City.KOLKATA;
import static com.services.domain.City.MUMBAI;
import static com.services.domain.Slot.AFTERNOON_SECOND;
import static com.services.domain.Slot.MORNING_FIRST;
import static com.services.domain.Slot.MORNING_SECOND;
import static java.util.Arrays.asList;

@RestController
@RequestMapping("db")
public class DatabaseController {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public DatabaseController(UserRepository userRepository,
                              EventRepository eventRepository,
                              RegistrationRepository registrationRepository){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    @PutMapping("populate")
    public void insertData() {
        insertIntoAllTables();
        insertEvents();
    }

    private void insertEvents() {
        Venue venue = Venue.builder()
                .name("Gymkhana")
                .city(KOLKATA.getValue())
                .build();

        TimeSlot timeSlot1 = TimeSlot.builder()
                .slotCode(MORNING_SECOND.getCode())
                .startTime(MORNING_SECOND.getStartTime())
                .endTime(MORNING_SECOND.getEndTime())
                .capacity(35)
                .venue(venue)
                .build();

        TimeSlot timeSlot2 = TimeSlot.builder()
                .slotCode(AFTERNOON_SECOND.getCode())
                .startTime(AFTERNOON_SECOND.getStartTime())
                .endTime(AFTERNOON_SECOND.getEndTime())
                .capacity(45)
                .venue(venue)
                .build();

        venue.setTimeSlots(asList(timeSlot1, timeSlot2));

        Event event = Event.builder()
                .code("MMA101")
                .name("MMA Workshop")
                .date(LocalDate.of(2017, 10, 15))
                .venue(venue)
                .build();

        eventRepository.save(event);
    }

    private void insertIntoAllTables(){
        User user = User.builder()
                .firstName("Jaymin")
                .lastName("Desai")
                .email("jaymindesai766@gmail.com")
                .contactDetails(Contact.builder()
                        .primary("9879008818")
                        .build())
                .addressDetails(Address.builder()
                        .house("B3")
                        .street("Sukhmani CHS")
                        .area("Airoli")
                        .state("Maharashtra")
                        .city(MUMBAI.getValue())
                        .zipCode("400708")
                        .build())
                .build();

        userRepository.save(user);

        Venue venue = Venue.builder()
                .name("Rajpath Club")
                .city(AHMEDABAD.getValue())
                .build();

        TimeSlot timeSlot1 = TimeSlot.builder()
                .slotCode(MORNING_FIRST.getCode())
                .startTime(MORNING_FIRST.getStartTime())
                .endTime(MORNING_FIRST.getEndTime())
                .capacity(20)
                .venue(venue)
                .build();

        TimeSlot timeSlot2 = TimeSlot.builder()
                .slotCode(AFTERNOON_SECOND.getCode())
                .startTime(AFTERNOON_SECOND.getStartTime())
                .endTime(AFTERNOON_SECOND.getEndTime())
                .capacity(30)
                .venue(venue)
                .build();

        venue.setTimeSlots(asList(timeSlot1, timeSlot2));

        Event event = Event.builder()
                .code("ML001")
                .name("Machine Learning Workshop")
                .date(LocalDate.of(2017, 8, 25))
                .venue(venue)
                .build();

        eventRepository.save(event);

        Registration registration = Registration.builder()
                .event(event)
                .user(user)
                .build();

        registrationRepository.save(registration);
    }
}

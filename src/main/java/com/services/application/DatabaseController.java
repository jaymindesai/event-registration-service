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
import com.services.infrastructure.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.services.domain.City.AHMEDABAD;
import static com.services.domain.City.MUMBAI;
import static java.util.Arrays.asList;

@RestController
@RequestMapping("db")
public class DatabaseController {

    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public DatabaseController(UserRepository userRepository,
                              VenueRepository venueRepository,
                              EventRepository eventRepository,
                              RegistrationRepository registrationRepository){
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    @RequestMapping("initiate")
    public void insertData() {
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
                        .city(MUMBAI.value())
                        .zipCode("400708")
                        .build())
                .build();

        userRepository.save(user);

        Venue venue = Venue.builder()
                .name("Rajpath Club")
                .city(AHMEDABAD.value())
                .build();

        TimeSlot timeSlot1 = TimeSlot.builder()
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(15, 30))
                .capacity(20)
                .venue(venue)
                .build();

        TimeSlot timeSlot2 = TimeSlot.builder()
                .startTime(LocalTime.of(16, 0))
                .endTime(LocalTime.of(17, 30))
                .capacity(30)
                .venue(venue)
                .build();

        venue.setTimeSlots(asList(timeSlot1, timeSlot2));

        venueRepository.save(venue);

        Event event = Event.builder()
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

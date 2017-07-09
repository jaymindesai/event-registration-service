package util;

import com.ers.Application;
import com.ers.domain.event.Event;
import com.ers.domain.event.TimeSlot;
import com.ers.domain.event.Venue;
import com.ers.domain.registration.Registration;
import com.ers.domain.user.Address;
import com.ers.domain.user.Contact;
import com.ers.domain.user.User;
import com.ers.infrastructure.EventRepository;
import com.ers.infrastructure.RegistrationRepository;
import com.ers.infrastructure.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static com.ers.domain.City.*;
import static com.ers.domain.Slot.*;
import static java.util.Arrays.asList;

@SuppressWarnings("ALL")
@ActiveProfiles(profiles = "db-setup")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InsertDataScript {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @BeforeClass
    public static void setUpOnce() throws SQLException {
        Connection Conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306?user=root&password=root");
        Statement statement = Conn.createStatement();
        statement.executeUpdate("CREATE DATABASE EVENT_REGISTRATION");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void populateDb(){
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
                .email("jaymeeen@gmail.com")
                .contactDetails(Contact.builder()
                        .primary("9988776655")
                        .build())
                .addressDetails(Address.builder()
                        .house("A1")
                        .street("Some Street")
                        .area("Some Area")
                        .state("Some State")
                        .city(MUMBAI.getValue())
                        .zipCode("654321")
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
                .timeSlot(timeSlot1)
                .build();

        registrationRepository.save(registration);
    }
}

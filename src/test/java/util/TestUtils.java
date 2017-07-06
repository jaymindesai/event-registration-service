package util;

import com.services.domain.event.Event;
import com.services.domain.event.TimeSlot;
import com.services.domain.event.Venue;
import com.services.domain.user.Address;
import com.services.domain.user.Contact;
import com.services.domain.user.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;

import static com.services.domain.City.KOLKATA;
import static com.services.domain.Slot.MORNING_SECOND;
import static java.util.Arrays.asList;
import static java.util.stream.Stream.of;

@SuppressWarnings("ALL")
public class TestUtils {

    public static HttpEntity<String> createEntity(String email) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("email", email);
        return new HttpEntity<>(httpHeaders);
    }

    public static Event someEvent(String eventCode) {
        Venue venue = Venue.builder()
                .name("Some Venue")
                .city(KOLKATA.getValue())
                .build();
        TimeSlot timeSlot1 = TimeSlot.builder()
                .slotCode(MORNING_SECOND.getCode())
                .startTime(MORNING_SECOND.getStartTime())
                .endTime(MORNING_SECOND.getEndTime())
                .capacity(40)
                .venue(venue)
                .build();
        venue.setTimeSlots(asList(timeSlot1));
        return Event.builder()
                .code(eventCode)
                .name("Some Name")
                .date(LocalDate.of(2017, 10, 15))
                .venue(venue)
                .build();
    }

    public static Event someEventWithCustomSlot(String eventCode, String slotCode, int capacity){
        return of(someEvent(eventCode))
                .peek(event -> {
                    event.getVenue().getTimeSlots().get(0).setSlotCode(slotCode);
                    event.getVenue().getTimeSlots().get(0).setCapacity(capacity);})
                .findFirst()
                .get();
    }

    public static User someUser() {
        return User.builder()
                .firstName("First")
                .lastName("Last")
                .email("testuser@ers.com")
                .contactDetails(Contact.builder()
                        .primary("9876543210")
                        .alternate("0123456789")
                        .build())
                .addressDetails(Address.builder()
                        .house("A1")
                        .street("Some Street")
                        .area("Some Area")
                        .state("Some State")
                        .city("Some City")
                        .zipCode("654321")
                        .build())
                .build();
    }

    public static User someUserWithEmail(String email) {
        return of(someUser())
                .peek(user -> user.setEmail(email))
                .findFirst()
                .get();
    }
}

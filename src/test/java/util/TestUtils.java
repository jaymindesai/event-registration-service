package util;

import com.ers.domain.event.Event;
import com.ers.domain.event.EventDto;
import com.ers.domain.event.TimeSlot;
import com.ers.domain.event.Venue;
import com.ers.domain.event.converters.EventConverter;
import com.ers.domain.event.converters.TimeSlotConverter;
import com.ers.domain.event.converters.VenueConverter;
import com.ers.domain.registration.Registration;
import com.ers.domain.user.Address;
import com.ers.domain.user.Contact;
import com.ers.domain.user.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.ers.domain.City.KOLKATA;
import static com.ers.domain.Slot.MORNING_SECOND;
import static java.util.Arrays.asList;

@SuppressWarnings("ALL")
public class TestUtils {

    private static TimeSlotConverter timeSlotConverter = new TimeSlotConverter();
    private static VenueConverter venueConverter = new VenueConverter(timeSlotConverter);
    private static EventConverter eventConverter = new EventConverter(venueConverter);

    public static HttpEntity<String> createEntity(String email) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("email", email);
        return new HttpEntity<>(httpHeaders);
    }

    public static Event someEvent(String eventCode) {
        Venue venue = someVenue();
        venue.setTimeSlots(asList(someTimeSlot(venue)));
        return Event.builder()
                .code(eventCode)
                .name("Some Name")
                .date(LocalDate.of(2017, 10, 15))
                .venue(venue)
                .build();
    }

    public static EventDto someEventDto(String eventCode){
        return eventConverter.convertToDto(someEvent(eventCode));
    }

    public static EventDto someEventDtoWithCustomSlot(String eventCode, String slotCode, int capacity){
        return eventConverter.convertToDto(someEventWithCustomSlot(eventCode, slotCode, capacity));
    }

    private static TimeSlot someTimeSlot(Venue venue) {
        return TimeSlot.builder()
                .slotCode(MORNING_SECOND.getCode())
                .startTime(MORNING_SECOND.getStartTime())
                .endTime(MORNING_SECOND.getEndTime())
                .capacity(40)
                .venue(venue)
                .build();
    }

    private static Venue someVenue() {
        return Venue.builder()
                .name("Some Venue")
                .city(KOLKATA.getValue())
                .build();
    }

    public static Event someEventWithCustomSlot(String eventCode, String slotCode, int capacity){
        return Stream.of(someEvent(eventCode))
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
        return Stream.of(someUser())
                .peek(user -> user.setEmail(email))
                .findFirst()
                .get();
    }

    public static Registration someRegistration() {
        return Registration.builder()
                .user(someUser())
                .event(someEvent("CODE549"))
                .timeSlot(someTimeSlot(someVenue()))
                .build();
    }
}

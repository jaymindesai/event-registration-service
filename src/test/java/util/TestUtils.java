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

    public static Event anEvent(String eventCode) {
        Venue venue = aVenue();
        venue.setTimeSlots(asList(aTimeSlot(venue)));
        return Event.builder()
                .code(eventCode)
                .name("Some Name")
                .date(LocalDate.of(2017, 10, 15))
                .venue(venue)
                .build();
    }

    public static EventDto anEventDto(String eventCode){
        return eventConverter.convertToDto(anEvent(eventCode));
    }

    public static EventDto anEventDtoWithCustomSlot(String eventCode, String slotCode, int capacity){
        return eventConverter.convertToDto(anEventWithCustomSlot(eventCode, slotCode, capacity));
    }

    private static TimeSlot aTimeSlot(Venue venue) {
        return TimeSlot.builder()
                .slotCode(MORNING_SECOND.getCode())
                .startTime(MORNING_SECOND.getStartTime())
                .endTime(MORNING_SECOND.getEndTime())
                .capacity(40)
                .venue(venue)
                .build();
    }

    private static Venue aVenue() {
        return Venue.builder()
                .name("Some Venue")
                .city(KOLKATA.getValue())
                .build();
    }

    public static Event anEventWithCustomSlot(String eventCode, String slotCode, int capacity){
        return Stream.of(anEvent(eventCode))
                .peek(event -> {
                    event.getVenue().getTimeSlots().get(0).setSlotCode(slotCode);
                    event.getVenue().getTimeSlots().get(0).setCapacity(capacity);})
                .findFirst()
                .get();
    }

    public static User sUser() {
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

    public static User aUserWithEmail(String email) {
        return Stream.of(sUser())
                .peek(user -> user.setEmail(email))
                .findFirst()
                .get();
    }

    public static Registration aRegistration() {
        return Registration.builder()
                .user(sUser())
                .event(anEvent("CODE549"))
                .timeSlot(aTimeSlot(aVenue()))
                .build();
    }
}

package integration;

import com.ers.domain.event.Event;
import com.ers.domain.event.EventDto;
import com.ers.infrastructure.EventRepository;
import com.ers.infrastructure.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.*;
import static util.TestUtils.*;

@SuppressWarnings("ALL")
public class EventControllerIT extends BaseIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldGetAllEvents(){
        //given
        final String email = "abc@xyz.com";
        insertUser(email); // Register a user so that header check passes. Unregistered user should not have access to events.
        insertEvent("CODE001");
        //when
        ResponseEntity<List<EventDto>> responseEntity = restTemplate.exchange("/events", GET, createEntity(email), new ParameterizedTypeReference<List<EventDto>>(){});
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().get(0).getCode()).isNotNull();
    }

    @Test
    public void shouldNotGetEventsIfUserUnregistered(){
        //given
        insertEvent("CODE055");
        //when
        ResponseEntity<List<EventDto>> responseEntity = restTemplate.exchange("/events", GET, EMPTY, new ParameterizedTypeReference<List<EventDto>>(){});
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(responseEntity.getBody().get(0).getCode()).isNull();
    }

    @Test
    public void shouldGetEvent(){
        //given
        insertEvent("CODE002");
        //when
        ResponseEntity<EventDto> responseEntity = restTemplate.getForEntity("/events/CODE002", EventDto.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo("CODE002");
    }

    @Test
    public void shouldUpdateSlotCapacity() {
        //given
        insertEventWithCustomSlot("CODE003", "SLOT001", 20);
        //when
        ResponseEntity<EventDto> responseEntity = restTemplate.exchange("/events/CODE003/timeSlots/SLOT001/capacity/30", PUT, EMPTY, EventDto.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(ACCEPTED);
        assertThat(responseEntity.getBody().getVenue().getTimeSlots().get(0).getCapacity()).isEqualTo(30);
    }

    @Test
    public void shouldDeleteEvent(){
        //given
        insertEvent("CODE004");
        //when
        restTemplate.delete("/events/CODE004");
        //then
        assertThat(getEvent("CODE004")).isNotPresent();
    }

    private void insertUser(String email){
        userRepository.save(someUserWithEmail(email));
    }

    private void insertEvent(String eventCode){
        eventRepository.save(someEvent(eventCode));
    }

    private void insertEventWithCustomSlot(String eventCode, String slotCode, int capacity){
        eventRepository.save(someEventWithCustomSlot(eventCode, slotCode, capacity));
    }

    private Optional<Event> getEvent(String eventCode){
        return eventRepository.findByCode(eventCode);
    }
}

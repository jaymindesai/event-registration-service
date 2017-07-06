package integration;

import com.services.domain.event.Event;
import com.services.domain.event.EventDto;
import com.services.infrastructure.EventRepository;
import com.services.infrastructure.UserRepository;
import integration.config.AbstractBaseIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;
import static util.TestUtils.*;

@SuppressWarnings("ALL")
public class EventControllerIT extends AbstractBaseIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldGetAllEvents(){
        //given
        final String email = "abc@xyz.com";
        addUser(email); // Register a user so that header check passes. Unregistered user should not have access to events.
        addEvent("CODE001");
        //when
        ResponseEntity<List<EventDto>> responseEntity = restTemplate.exchange("/events", GET, createEntity(email), new ParameterizedTypeReference<List<EventDto>>(){});
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().size()).isNotZero();
    }

    @Test
    public void shouldGetEvent(){
        //given
        addEvent("CODE002");
        //when
        ResponseEntity<EventDto> responseEntity = restTemplate.getForEntity("/events/CODE002", EventDto.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo("CODE002");
    }

    @Test
    public void shouldUpdateSlotCapacity() {
        //given
        addEventWithCustomSlot("CODE003", "SLOT001", 20);
        //when
        ResponseEntity<EventDto> responseEntity = restTemplate.exchange("/events/CODE003/timeSlots/SLOT001/capacity/30", PUT, EMPTY, EventDto.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(ACCEPTED);
        assertThat(responseEntity.getBody().getVenue().getTimeSlots().get(0).getCapacity()).isEqualTo(30);
    }

    @Test
    public void shouldDeleteEvent(){
        //given
        addEvent("CODE004");
        //when
        restTemplate.delete("/events/CODE004");
        //then
        assertThat(getEvent("CODE004")).isNull();
    }

    private void addUser(String email){
        userRepository.save(someUserWithEmail(email));
    }

    private void addEvent(String eventCode){
        eventRepository.save(someEvent(eventCode));
    }

    private void addEventWithCustomSlot(String eventCode, String slotCode, int capacity){
        eventRepository.save(someEventWithCustomSlot(eventCode, slotCode, capacity));
    }

    private Event getEvent(String eventCode){
        return eventRepository.findByCode(eventCode);
    }
}

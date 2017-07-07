package com.ers.application;

import com.ers.domain.event.EventDto;
import com.ers.domain.event.EventService;
import com.ers.domain.user.UserService;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.TestUtils.someEventDto;
import static util.TestUtils.someEventDtoWithCustomSlot;

@SuppressWarnings("ALL")
public class EventControllerTest {

    EventService eventService = mock(EventService.class);
    UserService userService = mock(UserService.class);

    EventController classUnderTest = createController();

    @Test
    public void shouldGetAllEvents() {
        //when
        List<EventDto> events = classUnderTest.getEvents();
        //then
        verify(userService).checkIfUserRegistered();
        verify(eventService).getEvents();
        assertThat(events.get(0).getCode()).isEqualTo("CODE1001");
    }

    @Test
    public void shouldGetEvent() {
        //when
        EventDto event = classUnderTest.getEvent("CODE1001");
        //then
        verify(eventService).findByCode("CODE1001");
        assertThat(event.getCode()).isEqualTo("CODE1001");
    }

    @Test
    public void shouldUpdateSlotCapacity() {
        //when
        EventDto event = classUnderTest.updateSlotCapacity("CODE1001", "SLOT1001", 20);
        //then
        verify(eventService).updateSlotCapacity("CODE1001", "SLOT1001", 20);
        assertThat(event.getVenue().getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getSlotCode().equals("SLOT1001"))
                .findFirst().get()
                .getCapacity()).isEqualTo(20);
    }

    @Test
    public void shouldDeleteEvent(){
        //when
        classUnderTest.deleteEvent("CODE1001");
        //then
        verify(eventService).deleteByCode("CODE1001");
    }

    private EventController createController() {
        //given
        when(eventService.getEvents()).thenReturn(asList(someEventDto("CODE1001")));
        when(eventService.findByCode(any(String.class))).thenReturn(someEventDto("CODE1001"));
        when(eventService.updateSlotCapacity(any(String.class), any(String.class), any(Integer.class)))
                .thenReturn(someEventDtoWithCustomSlot("CODE1001", "SLOT1001", 20));
        return new EventController(eventService, userService);
    }
}
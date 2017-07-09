package com.ers.application;

import com.ers.domain.event.EventDto;
import com.ers.domain.event.EventService;
import com.ers.domain.user.UserService;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("ALL")
public class EventControllerTest {

    EventService eventService = mock(EventService.class);
    UserService userService = mock(UserService.class);

    EventController controller = createController();

    private static final String EVENT_CODE = "CODE1001";
    private static final String SLOT_CODE = "SLOT1001";

    @Test
    public void shouldGetAllEvents() {
        //when
        List<EventDto> events = controller.getEvents();
        //then
        verify(userService).checkIfUserRegistered();
        verify(eventService).getEvents();
    }

    @Test
    public void shouldGetEvent() {
        //when
        EventDto event = controller.getEvent(EVENT_CODE);
        //then
        verify(eventService).findByCode(EVENT_CODE);
    }

    @Test
    public void shouldUpdateSlotCapacity() {
        //when
        EventDto event = controller.updateSlotCapacity(EVENT_CODE, SLOT_CODE, 20);
        //then
        verify(eventService).updateSlotCapacity(EVENT_CODE, SLOT_CODE, 20);
    }

    @Test
    public void shouldDeleteEvent(){
        //when
        controller.deleteEvent(EVENT_CODE);
        //then
        verify(eventService).deleteByCode(EVENT_CODE);
    }

    private EventController createController() {
        //given
        return new EventController(eventService, userService);
    }
}
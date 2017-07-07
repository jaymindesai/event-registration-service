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
import static util.TestUtils.anEventDto;
import static util.TestUtils.anEventDtoWithCustomSlot;

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
        assertThat(events.get(0).getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldGetEvent() {
        //when
        EventDto event = controller.getEvent(EVENT_CODE);
        //then
        verify(eventService).findByCode(EVENT_CODE);
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldUpdateSlotCapacity() {
        //when
        EventDto event = controller.updateSlotCapacity(EVENT_CODE, SLOT_CODE, 20);
        //then
        verify(eventService).updateSlotCapacity(EVENT_CODE, SLOT_CODE, 20);
        assertThat(event.getVenue().getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getSlotCode().equals(SLOT_CODE))
                .findFirst().get()
                .getCapacity()).isEqualTo(20);
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
        when(eventService.getEvents()).thenReturn(asList(anEventDto(EVENT_CODE)));
        when(eventService.findByCode(any(String.class))).thenReturn(anEventDto(EVENT_CODE));
        when(eventService.updateSlotCapacity(any(String.class), any(String.class), any(Integer.class)))
                .thenReturn(anEventDtoWithCustomSlot(EVENT_CODE, SLOT_CODE, 20));
        return new EventController(eventService, userService);
    }
}
package com.ers.domain.event;

import com.ers.application.handler.exceptions.NotFoundException;
import com.ers.domain.event.dto.EventDto;
import com.ers.infrastructure.EventRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.TestUtils.*;

@SuppressWarnings("ALL")
public class EventServiceTest {

    private EventRepository eventRepository = mock(EventRepository.class);
    private EventService service = new EventService(eventRepository);

    private Event event;
    private EventDto eventDto;

    private static final String EVENT_CODE = "CODE1001";
    private static final String SLOT_CODE = "SLOT1001";

    @Before
    public void setUp() {
        //given
        event = anEvent(EVENT_CODE);
        eventDto = anEventDto(EVENT_CODE);
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(ofNullable(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
    }

    @Test
    public void shouldFindEventByCode() {
        //when
        EventDto event = service.findByCode(EVENT_CODE);
        //then
        verify(eventRepository).findByCode(EVENT_CODE);
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldGetEvents() {
        //given
        when(eventRepository.findAll()).thenReturn(asList(event));
        //when
        List<EventDto> events = service.getEvents();
        //then
        verify(eventRepository).findAll();
        assertThat(events.get(0).getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileFetchingAllEvents() {
        //given
        when(eventRepository.findAll()).thenReturn(emptyList());
        //when //then
        assertThatThrownBy(service::getEvents)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No Events Found!");
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileCheckingWhetherEventValid() {
        //given
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(empty());
        //when
        Throwable throwable = catchThrowable(() -> service.findByCode(EVENT_CODE));
        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class)
                .hasMessage("Invalid event eventCode - " + EVENT_CODE + ", no such event found!");
    }

    @Test
    public void shouldReturnEvent() {
        //when
        Event event = service.event(EVENT_CODE);
        //then
        verify(eventRepository).findByCode(EVENT_CODE);
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldUpdateSlotCapacty() {
        //given
        Event eventWithUpdatedSlot = anEventWithCustomSlot(EVENT_CODE, SLOT_CODE, 20);
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(ofNullable(anEventWithCustomSlot(EVENT_CODE, SLOT_CODE, 40)));
        when(eventRepository.save(any(Event.class))).thenReturn(eventWithUpdatedSlot);
        //when
        EventDto updatedEvent = service.updateSlotCapacity(EVENT_CODE, SLOT_CODE, 20);
        //then
        verify(eventRepository).save(eventWithUpdatedSlot);
        assertThat(updatedEvent.getVenue().getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getSlotCode().equals(SLOT_CODE))
                .findFirst().get()
                .getCapacity()).isEqualTo(20);
    }

    @Test
    public void shouldDeleteEvent() {
        //when
        service.deleteByCode(EVENT_CODE);
        //then
        verify(eventRepository).deleteByCode(EVENT_CODE);
    }
}
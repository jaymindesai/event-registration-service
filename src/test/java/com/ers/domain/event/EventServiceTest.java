package com.ers.domain.event;

import com.ers.application.handler.exceptions.NotFoundException;
import com.ers.domain.event.converters.EventConverter;
import com.ers.infrastructure.EventRepository;
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

    EventConverter eventConverter = mock(EventConverter.class);
    EventRepository eventRepository = mock(EventRepository.class);

    EventService service = createService();

    private static final String EVENT_CODE = "CODE1001";
    private static final String SLOT_CODE = "SLOT1001";

    @Test
    public void shouldFindEventByCode() {
        //when
        EventDto event = service.findByCode(EVENT_CODE);
        //then
        verify(eventRepository).findByCode(EVENT_CODE);
        verify(eventConverter).convertToDto(anEvent(EVENT_CODE));
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldGetEvents() {
        //when
        List<EventDto> events = service.getEvents();
        //then
        verify(eventRepository).findAll();
        verify(eventConverter).convertToDto(any(Event.class));
        assertThat(events.get(0).getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileFetchingAllEvents() {
        //given
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAll()).thenReturn(emptyList());
        EventService service = new EventService(eventConverter, eventRepository);
        //when //then
        assertThatThrownBy(service::getEvents)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No Events Found!");
    }

    @Test
    public void shouldCheckWhetherEventValid() {
        //when
        Event event = service.isValidEvent(EVENT_CODE);
        //then
        verify(eventRepository).findByCode(EVENT_CODE);
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileCheckingWhetherEventValid() {
        //given
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(empty());
        EventService service = new EventService(eventConverter, eventRepository);
        //when
        Throwable throwable = catchThrowable(() -> service.isValidEvent(EVENT_CODE));
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
        Event event = anEventWithCustomSlot(EVENT_CODE, SLOT_CODE, 40);
        Event eventWithUpdatedSlot = anEventWithCustomSlot(EVENT_CODE, SLOT_CODE, 20);
        EventDto eventDtoWithUpdatedSlot = anEventDtoWithCustomSlot(EVENT_CODE, SLOT_CODE, 20);
        EventRepository eventRepository = mock(EventRepository.class);
        EventConverter eventConverter = mock(EventConverter.class);
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(ofNullable(event));
        when(eventRepository.save(any(Event.class))).thenReturn(eventWithUpdatedSlot);
        when(eventConverter.convertToDto(eventWithUpdatedSlot)).thenReturn(eventDtoWithUpdatedSlot);
        EventService service = new EventService(eventConverter, eventRepository);
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

    private EventService createService() {
        //given
        Event event = anEvent(EVENT_CODE);
        EventDto eventDto = anEventDto(EVENT_CODE);
        when(eventConverter.convertToDto(event)).thenReturn(eventDto);
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(ofNullable(event));
        when(eventRepository.findAll()).thenReturn(asList(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        return new EventService(eventConverter, eventRepository);
    }
}
package com.ers.domain.event;

import com.ers.domain.event.converters.EventConverter;
import com.ers.handler.exceptions.NotFoundException;
import com.ers.infrastructure.EventRepository;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.TestUtils.someEvent;
import static util.TestUtils.someEventDto;

@SuppressWarnings("ALL")
public class EventServiceTest {

    EventConverter eventConverter = mock(EventConverter.class);
    EventRepository eventRepository = mock(EventRepository.class);

    EventService service = createService();

    private static final String EVENT_CODE = "CODE1001";
    private static final String SLOT_CODE = "SLOT1001";

    @Test
    @Ignore
    public void shouldFindEventByCode(){
        //TODO: Fix this!!
        //when
        EventDto event = service.findByCode(EVENT_CODE);
        //then
        verify(eventRepository).findByCode(EVENT_CODE);
        verify(eventConverter).convertToDto(someEvent(EVENT_CODE));
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldGetEvents(){
        //when
        List<EventDto> events = service.getEvents();
        //then
        verify(eventRepository).findAll();
        verify(eventConverter).convertToDto(any(Event.class));
        assertThat(events.get(0).getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldThrowNoEventsFoundException() {
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
    public void shouldCheckWhetherEventValid(){
        //when
        Event event = service.isValidEvent(EVENT_CODE);
        //then
        verify(eventRepository).findByCode(EVENT_CODE);
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldThrowNoEventFoundException(){
        //given
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(Optional.empty());
        EventService service = new EventService(eventConverter, eventRepository);
        //when
        Throwable throwable = catchThrowable(() -> service.isValidEvent(EVENT_CODE));
        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class)
                .hasMessage("Invalid event eventCode - " + EVENT_CODE + ", no such event found!");
    }

    @Test
    public void shouldReturnEvent(){
        //when
        Event event = service.event(EVENT_CODE);
        //then
        verify(eventRepository).findByCode(EVENT_CODE);
        assertThat(event.getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    @Ignore
    public void shouldUpdateSlotCapacty(){
        //TODO: Fix this!!
        //when
        EventDto event = service.updateSlotCapacity(EVENT_CODE, SLOT_CODE, 20);
        //then
        verify(eventRepository).save(someEvent(EVENT_CODE));
    }

    @Test
    public void shouldDeleteEvent(){
        //when
        service.deleteByCode(EVENT_CODE);
        //then
        verify(eventRepository).deleteByCode(EVENT_CODE);
    }

    private EventService createService() {
        //given
        Event event = someEvent(EVENT_CODE);
        EventDto eventDto = someEventDto(EVENT_CODE);
        when(eventConverter.convertToDto(event)).thenReturn(eventDto);
        when(eventRepository.findByCode(EVENT_CODE)).thenReturn(Optional.ofNullable(event));
        when(eventRepository.findAll()).thenReturn(asList(event));
        when(eventRepository.save(event)).thenReturn(event);
        return new EventService(eventConverter, eventRepository);
    }
}
package com.ers.infrastructure;

import com.ers.domain.event.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static util.TestUtils.someEvent;

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    EventRepository repository;

    private static final String EVENT_CODE = "CODE1001";
    private static final String OTHER_EVENT_CODE = "CODE1002";

    @Test
    public void shouldFindByCode(){
        //given
        entityManager.persist(someEvent(EVENT_CODE));
        //when
        Optional<Event> event = repository.findByCode(EVENT_CODE);
        //then
        assertThat(event).isNotEmpty();
        assertThat(event.get().getCode()).isEqualTo(EVENT_CODE);
    }

    @Test
    public void shouldDeleteByCode(){
        //given
        entityManager.persist(someEvent(EVENT_CODE));
        entityManager.persist(someEvent(OTHER_EVENT_CODE));
        //when
        repository.deleteByCode(EVENT_CODE);
        //then
        Optional<Event> event = repository.findByCode(EVENT_CODE);
        Optional<Event> otherEvent = repository.findByCode(OTHER_EVENT_CODE);
        assertThat(event).isEmpty();
        assertThat(otherEvent).isNotEmpty();
        assertThat(otherEvent.get().getCode()).isEqualTo(OTHER_EVENT_CODE);
    }
}
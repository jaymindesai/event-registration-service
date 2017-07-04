package com.services.infrastructure;

import com.services.domain.event.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    Event findByCode(String code);

    void deleteByCode(String code);
}

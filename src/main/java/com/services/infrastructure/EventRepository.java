package com.services.infrastructure;

import com.services.domain.event.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    Optional<Event> findByCode(String code);

    void deleteByCode(String code);
}

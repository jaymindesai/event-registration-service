package com.services.infrastructure;

import com.services.domain.event.Event;
import com.services.domain.registration.Registration;
import com.services.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    Optional<Registration> findByEventAndUser(Event event, User user);
}

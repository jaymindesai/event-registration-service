package com.ers.infrastructure;

import com.ers.domain.event.Event;
import com.ers.domain.registration.Registration;
import com.ers.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    Optional<Registration> findByEventAndUser(Event event, User user);
}

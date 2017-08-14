package integration;

import com.ers.domain.registration.dto.RegistrationDto;
import com.ers.domain.user.User;
import com.ers.infrastructure.EventRepository;
import com.ers.infrastructure.RegistrationRepository;
import com.ers.infrastructure.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;
import static util.TestUtils.*;

@SuppressWarnings("ALL")
public class RegistrationIT extends AbstractBaseIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Test
    public void shouldGetAllRegistrations() {
        //given
        insertRegistration();
        //when
        ResponseEntity<List<RegistrationDto>> responseEntity =
                restTemplate.exchange("/registrations", GET, EMPTY, new ParameterizedTypeReference<List<RegistrationDto>>() {});
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().get(0).getRegistrantName()).isNotNull();
    }

    @Test
    public void shouldRegisterForAnEvent() {
        //given
        final String email = "fgh@mnb.com";
        insertUser(email);
        insertEventWithCustomSlot("CODE060", "SLOT080", 20);
        //when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("/registrations/event/CODE060/slot/SLOT080/user/" + getUserByEmail(email).getId(), String.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(ACCEPTED);
        assertThat(responseEntity.getBody()).isEqualTo("Registration Successful!");
    }

    @Test
    public void shouldNotRegisterIfRegistrationAlreadyExists() {
        //given
        final String email = "ksl@vmn.com";
        insertUser(email);
        insertEventWithCustomSlot("CODE070", "SLOT090", 20);
        restTemplate.getForEntity("/registrations/event/CODE070/slot/SLOT090/user/" + getUserByEmail(email).getId(), String.class);
        //when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("/registrations/event/CODE070/slot/SLOT090/user/" + getUserByEmail(email).getId(), String.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(CONFLICT);
    }

    private void insertUser(String email) {
        userRepository.save(aUserWithEmail(email));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    private void insertEventWithCustomSlot(String eventCode, String slotCode, int capacity) {
        eventRepository.save(anEventWithCustomSlot(eventCode, slotCode, capacity));
    }

    private void insertRegistration() {
        registrationRepository.save(aRegistration());
    }
}

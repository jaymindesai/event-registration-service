package integration;

import com.services.domain.registration.RegistrationDto;
import com.services.domain.user.User;
import com.services.infrastructure.EventRepository;
import com.services.infrastructure.RegistrationRepository;
import com.services.infrastructure.UserRepository;
import integration.config.AbstractBaseIT;
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
public class RegistrationControllerIT extends AbstractBaseIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Test
    public void shouldGetAllRegistrations(){
        //given
        insertRegistration();
        //when
        ResponseEntity<List<RegistrationDto>> responseEntity = restTemplate.exchange("/registrations", GET, EMPTY, new ParameterizedTypeReference<List<RegistrationDto>>(){});
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().get(0).getRegistrantName()).isNotNull();
    }

    @Test
    public void shouldRegisterForAnEvent(){
        //given
        final String email = "fgh@mnb.com";
        insertUser(email);
        insertEventWithCustomSlot("CODE060", "SLOT080", 20);
        //when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/registrations/event/CODE060/slot/SLOT080/user/" + getUserByEmail(email).getId(), null, String.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(ACCEPTED);
        assertThat(responseEntity.getBody()).isEqualTo("Registration Successful");
    }

    @Test
    public void shouldNotRegisterIfRegistrationAlreadyExists(){
        //given
        final String email = "ksl@vmn.com";
        insertUser(email);
        insertEventWithCustomSlot("CODE070", "SLOT090", 20);
        restTemplate.postForEntity("/registrations/event/CODE070/slot/SLOT090/user/" + getUserByEmail(email).getId(), null, String.class);
        //when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/registrations/event/CODE070/slot/SLOT090/user/" + getUserByEmail(email).getId(), null, String.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(CONFLICT);
    }

    private void insertUser(String email){
        userRepository.save(someUserWithEmail(email));
    }

    private User getUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

    private void insertEventWithCustomSlot(String eventCode, String slotCode, int capacity){
        eventRepository.save(someEventWithCustomSlot(eventCode, slotCode, capacity));
    }

    private void insertRegistration() {
        registrationRepository.save(someRegistration());
    }
}

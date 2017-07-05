package com.services.domain.user;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class User extends AbstractEntity {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    private Contact contactDetails;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private Address addressDetails;
}

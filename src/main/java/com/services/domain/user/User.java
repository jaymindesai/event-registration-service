package com.services.domain.user;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import static javax.persistence.CascadeType.ALL;

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

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    private Contact contactDetails;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private Address addressDetails;
}

package com.services.domain.user;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Address extends AbstractEntity {

    @Column(name = "HOUSE", nullable = true)
    private String house;

    @Column(name = "STREET", nullable = false)
    private String street;

    @Column(name = "AREA", nullable = true)
    private String area;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "ZIP_CODE", nullable = false)
    private String zipCode;
}

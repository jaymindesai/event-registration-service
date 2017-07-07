package com.ers.domain.user;

import com.ers.domain.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Contact extends AbstractEntity {

    @Column(name = "PRIMARY_CONTACT", nullable = false)
    private String primary;

    @Column(name = "ALTERNATE_CONTACT", nullable = true)
    private String alternate;
}

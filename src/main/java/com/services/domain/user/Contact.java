package com.services.domain.user;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.*;

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
